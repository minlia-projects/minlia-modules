package com.minlia.modules.security.authentication.jwt;

import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
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
        String subject = jwsClaims.getBody().getSubject();

        Date expirDate = jwsClaims.getBody().getExpiration();
        List<String> scopes = jwsClaims.getBody().get("scopes", List.class);
        List<GrantedAuthority> authorities = scopes.stream()
                .map(authority -> new SimpleGrantedAuthority(authority))
                .collect(Collectors.toList());

        UserContext context = UserContext.builder().username(subject).authorities(authorities).expireDate(expirDate).build();
        return new JwtAuthenticationToken(context, context.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
