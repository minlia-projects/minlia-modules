package com.minlia.modules.security.model.token;

import com.minlia.modules.security.exception.JwtExpiredTokenException;
import com.minlia.modules.security.exception.JwtInvalidTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RawAccessJwtToken implements JwtToken {

    private String token;

    public RawAccessJwtToken(String token) {
        this.token = token;
    }

    /**
     * Parses and validates JWT Token signature.
     */
    public Jws<Claims> parseClaims(String signingKey) {
        return this.parseClaims(token, signingKey);
    }

    public Jws<Claims> parseClaims(String token, String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid JWT Token", ex);
            throw new JwtInvalidTokenException("Invalid JWT token");
        } catch (ExpiredJwtException expiredEx) {
            log.info("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException(this, "JWT Token expired", expiredEx);
        }
    }

    public Jws<Claims> parseClaimsWithCache(String signingKey) {
        Jws<Claims> jwsClaims = this.parseClaims(signingKey);
        Object token = TokenCacheUtils.get(jwsClaims.getBody().get("key", String.class));
        if (null == token) {
            throw new JwtExpiredTokenException("JWT Token expired");
        } else {
            return this.parseClaims(token.toString(), signingKey);
        }
    }

    public Jws<Claims> parseClaimsWithGuid(String tokenKey, String signingKey) {
        Object token = TokenCacheUtils.get(tokenKey);
        if (null == token) {
            throw new JwtExpiredTokenException("JWT Token expired");
        } else {
            return this.parseClaims(token.toString(), signingKey);
        }
    }

    public void isSigned(String signingKey) {
        if (!Jwts.parser().setSigningKey(signingKey).isSigned(this.token)) {
            throw new JwtInvalidTokenException("Invalid JWT token");
        }
    }

    public void isExpired(String tokenKey) {
        if (!TokenCacheUtils.exists(tokenKey)) {
            throw new JwtExpiredTokenException("JWT Token expired");
        }
    }

    @Override
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
