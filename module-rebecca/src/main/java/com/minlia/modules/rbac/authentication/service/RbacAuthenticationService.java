package com.minlia.modules.rbac.authentication.service;

import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.service.UserQueryService;
import com.minlia.modules.rbac.util.SecurityUtil;
import com.minlia.modules.security.authentication.service.AbstractAuthenticationService;
import com.minlia.modules.security.model.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by will on 8/14/17.
 * 框架提供的抽象认证实现
 */
@Component
public class RbacAuthenticationService extends AbstractAuthenticationService {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserQueryService userQueryService;

    public Authentication authentication(Authentication authentication) {
        Assert.notNull(authentication, "No authentication data provided");
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        User user = userQueryService.findOneByUsernameOrEmailOrCellphone(username);
        if (null == user) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
        }

        if (user.getRoles() == null) {
            throw new InsufficientAuthenticationException("User has no roles assigned");
        }
        List<GrantedAuthority> authorities = SecurityUtil.getAuthorities(user.getRoles());

        UserContext userContext = UserContext.create(user.getUsername(), authorities);
        if (authorities == null || authorities.isEmpty()) {
            userContext.getAuthorities().add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return "REFRESH_TOKEN";
                }
            });
        }
        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
    }
}
