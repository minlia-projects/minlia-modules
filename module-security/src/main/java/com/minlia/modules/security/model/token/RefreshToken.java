package com.minlia.modules.security.model.token;

import com.minlia.modules.security.model.Scopes;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("unchecked")
public class RefreshToken implements JwtToken {
    private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates Refresh token 
     * 
     * @param token
     * @param signingKey
     * 
     * @return
     */
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        Jws<Claims> claims = token.parseClaims(signingKey);

        //TODO
//        List<String> permissions = claims.getBody().get("permissions", List.class);
//        if (CollectionUtils.isEmpty(permissions)) {
//            return Optional.empty();
//        }


//        List<String> scopes = claims.getBody().get("scopes", List.class);
//        if (scopes == null || scopes.isEmpty()
////                || !scopes.stream().filter(scope -> Scopes.REFRESH_TOKEN.authority().equals(scope)).findFirst().isPresent()
//) {
//            return Optional.empty();
//        } LoginCredentials


        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
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
