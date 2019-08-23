package com.minlia.modules.security.authentication.jwt;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.config.SysSecurityConfig;
import com.minlia.modules.security.exception.DefaultAuthenticationException;
import com.minlia.modules.security.exception.JwtInvalidTokenException;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtProperty jwtProperty;

    @Autowired
    private SysSecurityConfig sysSecurityConfig;

    @Autowired
    public JwtAuthenticationProvider(JwtProperty jwtProperty) {
        this.jwtProperty = jwtProperty;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> rawClaims = rawAccessToken.parseClaims(jwtProperty.getTokenSigningKey());
        String guid = rawClaims.getBody().get("guid", String.class);

        Jws<Claims> jwsClaims = rawAccessToken.parseClaimsWithGuid(guid, jwtProperty.getTokenSigningKey());

        if (!sysSecurityConfig.getMultiClientLogin() && !rawClaims.getBody().getId().equals(jwsClaims.getBody().getId())) {
            throw new DefaultAuthenticationException(SecurityCode.Exception.LOGGED_AT_ANOTHER_LOCATION);
        }

        String currdomain = jwsClaims.getBody().get("currdomain", String.class);
        checkDomain(currdomain);

        String username = jwsClaims.getBody().getSubject();

        Object orgId = jwsClaims.getBody().get("orgId");
        String cellphone = jwsClaims.getBody().get("cellphone", String.class);
        String email = jwsClaims.getBody().get("email", String.class);
        String currrole = jwsClaims.getBody().get("currrole", String.class);
        List<String> roles = jwsClaims.getBody().get("roles", List.class);
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
                .orgId(null != orgId ? Long.valueOf(orgId.toString()) : null)
                .username(username)
                .cellphone(cellphone)
                .email(email)
                .roles(roles)
                .currrole(currrole)
                .currdomain(currdomain)
                .permissions(permissions)
                .expireDate(LocalDateUtils.dateToLocalDateTime(expirDate))
                .build();

        //重置缓存时间
        TokenCacheUtils.expire(userContext.getGuid(), jwtProperty.getTokenExpirationTime());
        return new JwtAuthenticationToken(userContext, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    /**
     * 判断域名是否匹配
     *
     * @param currdomain
     */
    public void checkDomain(String currdomain) {
        if (StringUtils.isNotBlank(currdomain)) {
            ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest servletRequest = servletRequestAttributes.getRequest();
            try {
                URL url = new URL(servletRequest.getRequestURL().toString());
                if (!url.getHost().equals(currdomain)) {
                    throw new JwtInvalidTokenException("Invalid JWT token");
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

}
