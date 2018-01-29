package com.minlia.modules.security.authentication.ajax;

import org.springframework.security.authentication.AbstractAuthenticationToken;


/**
 * 匿名用户认证令牌
 */
public class AnonymousAuthenticationToken extends AbstractAuthenticationToken {

    public AnonymousAuthenticationToken() {
        super( null );
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }



}
