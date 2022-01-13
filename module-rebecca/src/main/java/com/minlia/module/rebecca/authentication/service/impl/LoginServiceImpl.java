package com.minlia.module.rebecca.authentication.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.rebecca.authentication.bean.UserLogonResponseBody;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserStatusEnum;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.exception.AjaxLockedException;
import com.minlia.modules.security.exception.DefaultAuthenticationException;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.AccessJwtToken;
import com.minlia.modules.security.model.token.JwtToken;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author garen
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final SysUserService sysUserService;
    private final SysRoleService sysRoleService;
    private final JwtTokenFactory tokenFactory;
    private final SysPermissionService sysPermissionService;

    public LoginServiceImpl(SysUserService sysUserService, SysRoleService sysRoleService, JwtTokenFactory tokenFactory, SysPermissionService sysPermissionService) {
        this.tokenFactory = tokenFactory;
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.sysPermissionService = sysPermissionService;
    }

    @Override
    public UserContext getUserContext(SysUserEntity userEntity, String currrole) {
        //如果当前角色为空获取默认角色
        if (StringUtils.isBlank(currrole)) {
            if (StringUtils.isNotBlank(userEntity.getDefaultRole())) {
                currrole = userEntity.getDefaultRole();
            } else {
                currrole = SecurityConstant.ROLE_GUEST_CODE;
            }
        }

        //获取用户所有角色
        List<String> roles = sysRoleService.getCodesByUserId(userEntity.getId());
        //判断是否包含登陆角色
        ApiAssert.state(roles.contains(currrole), SecurityCode.Message.NO_CURRENT_ROLE_PERMISSIONS);

        SysRoleEntity roleEntity = sysRoleService.getOne(Wrappers.<SysRoleEntity>lambdaQuery().eq(SysRoleEntity::getCode, currrole));
        List<String> permissions = sysPermissionService.getCodesByRoleId(roleEntity.getId());

        List<GrantedAuthority> authorities = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }

        UserContext userContext = UserContext.builder()
                .orgId(userEntity.getOrgId())
                .dpType(roleEntity.getDpType())
                .dpScope(roleEntity.getDpScope())
                .username(userEntity.getUsername())
                .areaCode(userEntity.getAreaCode())
                .cellphone(userEntity.getCellphone())
                .email(userEntity.getEmail())
                .uid(userEntity.getId())
                .currrole(currrole)
                .currdomain(roleEntity.getAccessDomain())
                .roles(sysRoleService.getCodesByUserId(userEntity.getId()))
                .permissions(permissions)
                .authorities(authorities)
                .build();
        return userContext;
    }

    @Override
    public UserLogonResponseBody loginWithRole(SysUserEntity entity, String currrole) {
        //如果当前角色为空获取默认角色
        UserContext userContext = this.getUserContext(entity, currrole);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        String id = UUID.randomUUID().toString();
        AccessJwtToken accessToken = tokenFactory.createAccessJwtToken(userContext.getKey(), null, id);
        AccessJwtToken refreshToken = tokenFactory.createRefreshToken(userContext.getKey(), null, id);
        AccessJwtToken rawToken = tokenFactory.createRawJwtToken(userContext, id);

        //缓存token
        TokenCacheUtils.kill(userContext.getKey());
        TokenCacheUtils.cache(userContext.getKey(), rawToken.getToken(), tokenFactory.getSettings().getTokenExpirationTime());
        return UserLogonResponseBody.builder()
                .token(accessToken.getToken())
                .loginEffectiveDate((accessToken).getClaims().getExpiration().getTime())
                .refreshToken(refreshToken.getToken())
                .build();
    }

    @Override
    public Map getLoginInfoByUid(Long uid) {
        SysUserEntity userEntity = sysUserService.getById(uid);
        return this.getLoginInfo(userEntity, userEntity.getDefaultRole());
    }

    @Override
    public Map getLoginInfoByCellphone(String cellphone) {
        SysUserEntity userEntity = sysUserService.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getCellphone, cellphone));
        return this.getLoginInfo(userEntity, userEntity.getDefaultRole());
    }

    private Map getLoginInfo(SysUserEntity userEntity, String currrole) {
        if (null != userEntity.getAccountEffectiveDate() && userEntity.getAccountEffectiveDate().isBefore(LocalDateTime.now())) {
            throw new AccountExpiredException("账号已过期");
        } else if (userEntity.getDisFlag()) {
            throw new DisabledException("账号已禁用");
        } else if (SysUserStatusEnum.TERMINATED.equals(userEntity.getStatus())) {
            throw new DefaultAuthenticationException(SysUserCode.Message.ALREADY_TERMINATED);
        } else if (userEntity.getLockFlag() && LocalDateTime.now().isBefore(userEntity.getLockTime())) {
            throw new AjaxLockedException("账号已锁定", ChronoUnit.SECONDS.between(LocalDateTime.now(), userEntity.getLockTime()));
        }

        UserContext userContext = this.getUserContext(userEntity, userEntity.getDefaultRole());
        String id = UUID.randomUUID().toString();
        String key = userContext.getKey();
        JwtToken accessToken = tokenFactory.createAccessJwtToken(key, null, id);
        JwtToken refreshToken = tokenFactory.createRefreshToken(key, null, id);
        JwtToken rawToken = tokenFactory.createRawJwtToken(userContext, id);

        Map<String, Object> tokenMap = Maps.newHashMap();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("expireDate", ((AccessJwtToken) accessToken).getClaims().getExpiration());
        tokenMap.put("refreshToken", refreshToken.getToken());

        TokenCacheUtils.kill(key);
        TokenCacheUtils.cache(key, rawToken.getToken(), tokenFactory.getSettings().getTokenExpirationTime());
        return tokenMap;
    }

}
