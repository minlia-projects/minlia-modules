package com.minlia.modules.rebecca.service.impl;

import com.google.common.collect.Lists;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.body.UserLogonResponseBody;
import com.minlia.modules.rebecca.service.LoginService;
import com.minlia.modules.rebecca.service.PermissionService;
import com.minlia.modules.rebecca.service.RoleService;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.AccessJwtToken;
import com.minlia.modules.security.model.token.JwtToken;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private JwtTokenFactory tokenFactory;

    @Autowired
    private PermissionService permissionService;

//    @Autowired
//    private NavigationService navigationService;

    @Override
    public UserContext getUserContext(User user, String currrole) {
        //如果当前角色为空获取默认角色
        if (StringUtils.isBlank(currrole)) {
            if (StringUtils.isNotBlank(user.getDefaultRole())) {
                currrole = user.getDefaultRole();
            } else {
                currrole = SecurityConstant.ROLE_GUEST_CODE;
            }
        }

        List<String> roles = roleService.queryCodeByUserId(user.getId());
//        Role role = roleService.queryByCode(currrole);
//        List<MyNavigationVO> navigations = navigationService.queryMyNavigationList(NavigationQO.builder().isOneLevel(true).display(true).roleId(role.getId()).build());
        List<String> permissions = permissionService.getPermissionCodes(Lists.newArrayList(currrole));
        List<GrantedAuthority> authorities = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }

        String currrdomain = roleService.queryByCode(currrole).getAccessDomain();

        UserContext userContext = UserContext.builder()
                .orgId(user.getOrgId())
                .username(user.getUsername())
                .cellphone(user.getCellphone())
                .email(user.getEmail())
                .guid(user.getGuid())
                .currrole(currrole)
                .currdomain(currrdomain)
                .roles(roles)
//                .navigations(navigations)
                .permissions(permissions)
                .authorities(authorities)
                .build();
        return userContext;
    }

    @Override
    public HashMap getLoginInfoByUser(User user, String currrole) {
        //如果当前角色为空获取默认角色
        UserContext userContext = this.getUserContext(user, user.getDefaultRole());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        String id = UUID.randomUUID().toString();
        AccessJwtToken accessToken = tokenFactory.createAccessJwtToken(userContext, id);
        AccessJwtToken refreshToken = tokenFactory.createRefreshToken(userContext, id);
        AccessJwtToken rawToken = tokenFactory.createRawJwtToken(userContext, id);

        HashMap tokenMap = new HashMap();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("accountEffectiveDate", (accessToken).getClaims().getExpiration().getTime());
        tokenMap.put("refreshToken", refreshToken.getToken());

        //缓存token TODO
        TokenCacheUtils.kill(userContext.getGuid());
        TokenCacheUtils.cache(userContext.getGuid(), rawToken.getToken(), tokenFactory.getSettings().getTokenExpirationTime());
        return tokenMap;
    }
    @Override
    public UserLogonResponseBody loginWithRole(User user, String currentRole) {
        //如果当前角色为空获取默认角色
        UserContext userContext = this.getUserContext(user, user.getDefaultRole());
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);

        String id = UUID.randomUUID().toString();
        AccessJwtToken accessToken = tokenFactory.createAccessJwtToken(userContext, id);
        AccessJwtToken refreshToken = tokenFactory.createRefreshToken(userContext, id);
        AccessJwtToken rawToken = tokenFactory.createRawJwtToken(userContext, id);


        UserLogonResponseBody userLogonResponseBody=UserLogonResponseBody.builder()
                .token(accessToken.getToken())
                .loginEffectiveDate((accessToken).getClaims().getExpiration().getTime())
                .refreshToken(refreshToken.getToken())
                .build();

        //缓存token TODO
        TokenCacheUtils.kill(userContext.getGuid());
        TokenCacheUtils.cache(userContext.getGuid(), rawToken.getToken(), tokenFactory.getSettings().getTokenExpirationTime());

//        HashMap tokenMap = new HashMap();
//        tokenMap.put("token", accessToken.getToken());
//        tokenMap.put("accountEffectiveDate", (accessToken).getClaims().getExpiration().getTime());
//        tokenMap.put("refreshToken", refreshToken.getToken());
        return userLogonResponseBody;
    }

}
