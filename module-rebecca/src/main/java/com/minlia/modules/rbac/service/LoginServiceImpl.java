package com.minlia.modules.rbac.service;

import com.google.common.collect.Lists;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.security.constant.SecurityConstant;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.AccessJwtToken;
import com.minlia.modules.security.model.token.JwtTokenFactory;
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

        UserContext userContext = UserContext.builder()
                .username(user.getUsername())
                .cellphone(user.getCellphone())
                .email(user.getEmail())
                .guid(user.getGuid())
                .currrole(currrole)
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

        AccessJwtToken accessToken = this.tokenFactory.createAccessJwtToken(userContext);
        AccessJwtToken refreshToken = this.tokenFactory.createRefreshToken(userContext);
        HashMap tokenMap = new HashMap();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("expireDate", (accessToken).getClaims().getExpiration().getTime());
        tokenMap.put("refreshToken", refreshToken.getToken());
        return tokenMap;
    }

}
