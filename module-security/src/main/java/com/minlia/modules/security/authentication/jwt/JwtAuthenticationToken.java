package com.minlia.modules.security.authentication.jwt;

import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.exception.JwtExpiredTokenException;
import com.minlia.modules.security.exception.JwtInvalidTokenException;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author garen
 */
@Slf4j
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;

    private Object credentials;

    public JwtAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
        this.credentials = null;
    }

    public Jws<Claims> parseClaimsWithKey(String tokenKey) {
        String token = TokenCacheUtils.get(tokenKey);
        if (null == token) {
            throw new JwtExpiredTokenException("JWT Token expired");
        } else {
            return JwtTokenParser.parse(token, getSigningKey());
        }
    }

    public void isSigned(String signingKey) {
        if (!Jwts.parser().setSigningKey(signingKey).isSigned(this.principal.toString())) {
            throw new JwtInvalidTokenException("Invalid JWT token");
        }
    }

    public void isExpired(String tokenKey) {
        if (!TokenCacheUtils.exists(tokenKey)) {
            throw new JwtExpiredTokenException("JWT Token expired");
        }
    }

    private String getSigningKey() {
        JwtProperty jwtProperty = (JwtProperty) this.credentials;
        return jwtProperty.getTokenSigningKey();
    }

}