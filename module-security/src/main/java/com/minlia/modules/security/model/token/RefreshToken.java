package com.minlia.modules.security.model.token;

import com.minlia.modules.security.authentication.jwt.JwtAuthenticationToken;
import com.minlia.modules.security.authentication.jwt.JwtTokenParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.Data;

import java.util.Optional;

/**
 * @author garen
 */
@Data
public class RefreshToken {

    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates Refresh token
     *
     * @param token
     * @param signingKey
     * @return
     */
    public static Optional<RefreshToken> create(JwtAuthenticationToken token, String signingKey) {
        Jws<Claims> claims = JwtTokenParser.parse((String) token.getPrincipal(), signingKey);
        return Optional.of(new RefreshToken(claims));
    }

    public Jws<Claims> getClaims() {
        return claims;
    }

    public String getJti() {
        return claims.getBody().getId();
    }

    public String getSubject() {
        return claims.getBody().getSubject();
    }
}
