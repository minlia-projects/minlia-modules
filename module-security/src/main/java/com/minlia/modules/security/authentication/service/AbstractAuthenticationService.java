package com.minlia.modules.security.authentication.service;

import org.springframework.security.core.Authentication;

/**
 * Created by will on 8/14/17.
 * 框架提供的抽象认证实现
 */
public abstract class AbstractAuthenticationService implements AuthenticationService {

    @Override
    public Authentication authentication(Authentication authentication) {
        return authentication;
    }
//    public boolean authentication(Authentication authentication) {
//        return false;
//    }

//    public boolean authentication(Authentication authentication) {
//        Assert.notNull(authentication, "No authentication data provided");
//        String username = (String) authentication.getPrincipal();
//        String password = (String) authentication.getCredentials();
//        User user = userQueryService.findOneByUsernameOrEmailOrCellphone(username);
//        if (null == user) {
//            throw new UsernameNotFoundException("User not found: " + username);
//        }
//        if (!encoder.matches(password, user.getPassword())) {
//            throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
//        }
////        if (user.getRoles() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
////        List<GrantedAuthority> authorities = securityService.getAuthorities(user.getRoles());
////        UserContext userContext = UserContext.create(user.getUsername(), authorities);
////        return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
//
//        return null;
//    }

}
