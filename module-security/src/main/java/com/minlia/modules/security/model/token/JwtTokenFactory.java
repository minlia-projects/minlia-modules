package com.minlia.modules.security.model.token;

import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.model.Scopes;
import com.minlia.modules.security.model.UserContext;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
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
        if (StringUtils.isBlank(userContext.getUsername())) {
//            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("guid", userContext.getGuid());
        claims.put("cellphone", userContext.getCellphone());
        claims.put("email", userContext.getEmail());
        claims.put("currrole", userContext.getCurrrole());
        claims.put("roles", userContext.getRoles());
//        claims.put("navigations", userContext.getNavigations());
        claims.put("permissions", userContext.getPermissions());

        LocalDateTime currentTime = LocalDateTime.now();

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime.plusMinutes(settings.getTokenExpirationTime()).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }

    public AccessJwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
//            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }

        LocalDateTime currentTime = LocalDateTime.now();

        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("guid", userContext.getGuid());
        claims.put("cellphone", userContext.getCellphone());
        claims.put("email", userContext.getEmail());
        claims.put("currrole", userContext.getCurrrole());
        claims.put("roles", userContext.getRoles());
//        claims.put("navigations", userContext.getNavigations());
        claims.put("permissions", Arrays.asList(Scopes.REFRESH_TOKEN.name()));

        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(currentTime.plusMinutes(settings.getRefreshTokenExpTime()).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();

        return new AccessJwtToken(token, claims);
    }
}
