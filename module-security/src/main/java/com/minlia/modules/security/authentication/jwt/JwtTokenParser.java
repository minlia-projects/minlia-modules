package com.minlia.modules.security.authentication.jwt;

import com.minlia.modules.security.exception.JwtExpiredTokenException;
import com.minlia.modules.security.exception.JwtInvalidTokenException;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author garen
 */
@Slf4j
public class JwtTokenParser {

    public static Jws<Claims> parse(String token, String signingKey) {
        try {
            return Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
        } catch (UnsupportedJwtException | MalformedJwtException | IllegalArgumentException | SignatureException ex) {
            log.error("Invalid JWT Token", ex);
            throw new JwtInvalidTokenException("Invalid JWT token");
        } catch (ExpiredJwtException expiredEx) {
            log.error("JWT Token is expired", expiredEx);
            throw new JwtExpiredTokenException("JWT Token expired", expiredEx);
        }
    }

}
