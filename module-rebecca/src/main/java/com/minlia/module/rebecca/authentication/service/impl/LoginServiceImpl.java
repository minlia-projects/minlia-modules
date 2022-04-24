package com.minlia.module.rebecca.authentication.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.rebecca.authentication.service.LoginService;
import com.minlia.module.rebecca.permission.service.SysPermissionService;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.JwtToken;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

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
    private final JwtTokenFactory jwtTokenFactory;
    private final SysPermissionService sysPermissionService;

    public LoginServiceImpl(SysUserService sysUserService, SysRoleService sysRoleService, JwtTokenFactory jwtTokenFactory, SysPermissionService sysPermissionService) {
        this.sysUserService = sysUserService;
        this.sysRoleService = sysRoleService;
        this.jwtTokenFactory = jwtTokenFactory;
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

        UserContext userContext = UserContext.builder().orgId(userEntity.getOrgId()).dpType(roleEntity.getDpType()).dpScope(roleEntity.getDpScope()).username(userEntity.getUsername()).areaCode(userEntity.getAreaCode()).cellphone(userEntity.getCellphone()).email(userEntity.getEmail()).uid(userEntity.getId()).currrole(currrole).currdomain(roleEntity.getAccessDomain()).roles(sysRoleService.getCodesByUserId(userEntity.getId())).permissions(permissions).authorities(authorities).build();
        return userContext;
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
        UserContext userContext = this.getUserContext(userEntity, userEntity.getDefaultRole());
        String id = UUID.randomUUID().toString();
        String key = userContext.getKey();
        JwtToken accessToken = jwtTokenFactory.createAccessJwtToken(key, null, id);
        JwtToken refreshToken = jwtTokenFactory.createRefreshToken(key, null, id);
        JwtToken rawToken = jwtTokenFactory.createRawJwtToken(userContext, id);

        Map<String, Object> tokenMap = Maps.newHashMap();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("expireDate", accessToken.getClaims().getExpiration());
        tokenMap.put("refreshToken", refreshToken.getToken());

        TokenCacheUtils.kill(key);
        TokenCacheUtils.cache(key, rawToken.getToken(), jwtTokenFactory.getSettings().getTokenExpirationTime());
        return tokenMap;
    }

}
