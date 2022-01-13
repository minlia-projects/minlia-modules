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

    public AccessJwtToken createAccessJwtToken(String key, String username, String id) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("key", key);
        return getJwtToken(claims, settings.getTokenExpirationTime(), id);
    }

    public AccessJwtToken createRefreshToken(String key, String username, String id) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("key", key);
        return getJwtToken(claims, settings.getRefreshTokenExpTime(), id);
    }

    public AccessJwtToken createRawJwtToken(UserContext userContext, String id) {
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("uid", userContext.getUid());
        claims.put("orgId", userContext.getOrgId());
        claims.put("dpType", userContext.getDpType());
        claims.put("dpScope", userContext.getDpScope());
        claims.put("areaCode", userContext.getAreaCode());
        claims.put("cellphone", userContext.getCellphone());
        claims.put("email", userContext.getEmail());
        claims.put("currrole", userContext.getCurrrole());
        claims.put("currdomain", userContext.getCurrdomain());
        claims.put("roles", userContext.getRoles());
        claims.put("permissions", userContext.getPermissions());
        return getJwtToken(claims, settings.getTokenExpirationTime(), id);
    }

    private AccessJwtToken getJwtToken(Claims claims, Integer tokenExpirationTime, String id) {
        LocalDateTime currentTime = LocalDateTime.now();
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuer(settings.getTokenIssuer())
                .setId(id)
                .setIssuedAt(LocalDateUtils.localDateTimeToDate(currentTime))
//                .setNotBefore()   //不能早于这个时间使用
//                .setExpiration(LocalDateUtils.localDateTimeToDate(currentTime.plusMinutes(tokenExpirationTime)))
                .setExpiration(LocalDateUtils.localDateTimeToDate(LocalDateTime.now().plusMonths(1)))
                .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
                .compact();
        return new AccessJwtToken(token, claims);
    }

}
