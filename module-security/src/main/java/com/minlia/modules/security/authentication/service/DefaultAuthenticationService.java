package com.minlia.modules.security.authentication.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Created by will on 8/14/17.
 * 框架提供的抽象认证实现
 */
@Service
public class DefaultAuthenticationService extends AbstractAuthenticationService {

//    @Override
//    public boolean authentication(Authentication authentication) {
//        return true;
//    }

    @Override
    public Authentication authentication(Authentication authentication) {
        return authentication;
//        return new AnonymousAuthenticationToken();
//        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        UserContext userContext = UserContext.create("test", authorities);
//        return new UsernamePasswordAuthenticationToken(userContext, null, authorities);
    }
}
