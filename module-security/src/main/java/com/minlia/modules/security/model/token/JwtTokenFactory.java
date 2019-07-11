package com.minlia.modules.security.model.token;

import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class JwtTokenFactory {

    private final JwtProperty settings;

    public JwtProperty getSettings() {
        return settings;
    }

    @Autowired
    public JwtTokenFactory(JwtProperty settings) {
        this.settings = settings;
    }

    /**
     * Factory method for issuing new JWT Tokens.
     *
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("guid", userContext.getGuid());
        return getJwtToken(claims, settings.getTokenExpirationTime());
    }

    /**
     * Factory method for issuing new JWT Tokens.
     *
     * @return
     */
    public AccessJwtToken createRawJwtToken(UserContext userContext) {
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("guid", userContext.getGuid());
        claims.put("cellphone", userContext.getCellphone());
        claims.put("email", userContext.getEmail());
        claims.put("currrole", userContext.getCurrrole());
        claims.put("roles", userContext.getRoles());
//        claims.put("navigations", userContext.getNavigations());
        claims.put("permissions", userContext.getPermissions());

        return getJwtToken(claims, settings.getTokenExpirationTime());
    }

    public AccessJwtToken createRefreshToken(UserContext userContext) {
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("guid", userContext.getGuid());
        return getJwtToken(claims, settings.getRefreshTokenExpTime());
    }

    private AccessJwtToken getJwtToken(Claims claims, Integer tokenExpirationTime) {
        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(LocalDateUtils.localDateTimeToDate(currentTime))
//                .setExpiration(LocalDateUtils.localDateTimeToDate(currentTime.plusMinutes(tokenExpirationTime)))
                .setExpiration(LocalDateUtils.localDateTimeToDate(LocalDateTime.MAX.withYear(9999)))       //设置永不过期 TODO
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
        return new AccessJwtToken(token, claims);
    }

}
