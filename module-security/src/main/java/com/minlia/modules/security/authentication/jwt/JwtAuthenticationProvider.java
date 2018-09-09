package com.minlia.modules.security.authentication.jwt;

import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProperty jwtProperty;

    @Autowired
    public JwtAuthenticationProvider(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtProperty.getTokenSigningKey());
        String username = jwsClaims.getBody().getSubject();
        String guid = jwsClaims.getBody().get("guid", String.class);
        String currrole = jwsClaims.getBody().get("currrole", String.class);
        Date expirDate = jwsClaims.getBody().getExpiration();

        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());
        UserContext userContext = UserContext.builder().username(username).guid(guid).currrole(currrole).authorities(authorities).expireDate(expirDate).build();

        return new JwtAuthenticationToken(userContext, userContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
