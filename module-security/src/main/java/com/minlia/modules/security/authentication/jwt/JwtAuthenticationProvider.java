package com.minlia.modules.security.authentication.jwt;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

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
        String cellphone = jwsClaims.getBody().get("cellphone", String.class);
        String email = jwsClaims.getBody().get("email", String.class);
        String currrole = jwsClaims.getBody().get("currrole", String.class);
        List<String> roles = jwsClaims.getBody().get("roles", List.class);
//        List navigations = jwsClaims.getBody().get("navigations", List.class);
        List<String> permissions = jwsClaims.getBody().get("permissions", List.class);
        Date expirDate = jwsClaims.getBody().getExpiration();

        List<GrantedAuthority> authorities = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }

        UserContext userContext = UserContext.builder()
                .guid(guid)
                .username(username)
                .cellphone(cellphone)
                .email(email)
                .roles(roles)
                .currrole(currrole)
//                .navigations(navigations)
                .permissions(permissions)
                .expireDate(LocalDateUtils.dateToLocalDateTime(expirDate))
                .build();
        return new JwtAuthenticationToken(userContext, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
