package com.minlia.module.rebecca.authentication.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.rebecca.authentication.bean.UserLogonResponseBody;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.AccessJwtToken;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author garen
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final SysRoleService sysRoleService;
    private final JwtTokenFactory tokenFactory;
    private final SysPermissionService sysPermissionService;

    public LoginServiceImpl(SysRoleService sysRoleService, JwtTokenFactory tokenFactory, SysPermissionService sysPermissionService) {
        this.sysRoleService = sysRoleService;
        this.tokenFactory = tokenFactory;
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

}
