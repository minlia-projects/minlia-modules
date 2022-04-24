//package com.minlia.modules.security.model.token;
//
//import com.minlia.modules.security.exception.JwtExpiredTokenException;
//import com.minlia.modules.security.exception.JwtInvalidTokenException;
//import io.jsonwebtoken.*;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AbstractAuthenticationToken;
//import org.springframework.util.Assert;
//
//@Slf4j
//public class JwtAuthenticationToken extends AbstractAuthenticationToken {
//
//    private final String principal;
//
//    private Object credentials;
//
//    public JwtAuthenticationToken(String principal, Object credentials) {
//        super(null);
//        this.principal = principal;
//        this.credentials = credentials;
//        this.setAuthenticated(false);
//    }
//
//    /**
//     * Parses and validates JWT Token signature.
//     */
//    public Jws<Claims> parseClaims(String signingKey) {
//        return this.parseClaims(principal, signingKey);
//    }
//
//    public Jws<Claims> parseClaims(String token, String signingKey) {
//        try {
//            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
//        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
//            log.error("Invalid JWT Token", ex);
//            throw new JwtInvalidTokenException("Invalid JWT token");
//        } catch (ExpiredJwtException expiredEx) {
//            log.error("JWT Token is expired", expiredEx);
//            throw new JwtExpiredTokenException("JWT Token expired", expiredEx);
//        }
//    }
//
//    public Jws<Claims> parseClaimsWithKey(String tokenKey, String signingKey) {
//        String token = TokenCacheUtils.get(tokenKey);
//        if (null == token) {
//            throw new JwtExpiredTokenException("JWT Token expired");
//        } else {
//            return this.parseClaims(token, signingKey);
//        }
//    }
//
//    public void isSigned(String signingKey) {
//        if (!Jwts.parser().setSigningKey(signingKey).isSigned(this.principal)) {
//            throw new JwtInvalidTokenException("Invalid JWT token");
//        }
//    }
//
//    public void isExpired(String tokenKey) {
//        if (!TokenCacheUtils.exists(tokenKey)) {
//            throw new JwtExpiredTokenException("JWT Token expired");
//        }
//    }
//
//    @Override
//    public Object getCredentials() {
//        return this.credentials;
//    }
//
//    @Override
//    public Object getPrincipal() {
//        return this.principal;
//    }
//
//    @Override
//    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
//        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
//        super.setAuthenticated(false);
//    }
//
//    @Override
//    public void eraseCredentials() {
//        super.eraseCredentials();
//        this.credentials = null;
//    }
//
//}