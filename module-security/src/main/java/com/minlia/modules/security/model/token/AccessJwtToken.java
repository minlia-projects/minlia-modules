package com.minlia.modules.security.model.token;

import io.jsonwebtoken.Claims;

public final class AccessJwtToken implements JwtToken {
    private final String rawToken;
    private Claims claims;

    public AccessJwtToken(final String token, Claims claims) {
        this.rawToken = token;
        this.claims = claims;
    }

    public String getToken() {
        return this.rawToken;
    }

    public Claims getClaims() {
        return claims;
    }
}
