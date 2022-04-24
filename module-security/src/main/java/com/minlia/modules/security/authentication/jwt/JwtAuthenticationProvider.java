package com.minlia.modules.security.authentication.jwt;

import com.google.common.collect.Lists;
import com.minlia.cloud.utils.LocalDateUtils;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.config.SysSecurityConfig;
import com.minlia.modules.security.exception.DefaultAuthenticationException;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.TokenCacheUtils;
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

import java.util.List;

/**
 * @author garen
 */
@Slf4j
@Component
public class JwtAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private SysSecurityConfig sysSecurityConfig;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String principal = (String) authentication.getPrincipal();
        JwtProperty credentials = (JwtProperty) authentication.getCredentials();
        Jws<Claims> jwsClaims = JwtTokenParser.parse(principal, credentials.getTokenSigningKey());
        String tokenKey = jwsClaims.getBody().get("key", String.class);
        String token = TokenCacheUtils.get(tokenKey);
        Jws<Claims> rawClaims = JwtTokenParser.parse(token, credentials.getTokenSigningKey());
        //是否允许多端登陆
        if (!sysSecurityConfig.getMultiClientLogin() && !rawClaims.getBody().getId().equals(jwsClaims.getBody().getId())) {
            throw new DefaultAuthenticationException(SecurityCode.Exception.LOGGED_AT_ANOTHER_LOCATION);
        }
        //域名控制
        String currdomain = rawClaims.getBody().get("currdomain", String.class);
        //checkDomain(currdomain);
        List<String> permissions = rawClaims.getBody().get("permissions", List.class);
        UserContext userContext = UserContext.builder().uid(rawClaims.getBody().get("uid", Long.class)).orgId(Long.valueOf(rawClaims.getBody().get("orgId").toString())).dpType(rawClaims.getBody().get("dpType", Integer.class)).dpScope(rawClaims.getBody().get("dpScope", String.class)).username(rawClaims.getBody().getSubject()).areaCode(rawClaims.getBody().get("areaCode", Integer.class)).cellphone(rawClaims.getBody().get("cellphone", String.class)).email(rawClaims.getBody().get("email", String.class)).roles(rawClaims.getBody().get("roles", List.class)).currrole(rawClaims.getBody().get("currrole", String.class)).currdomain(currdomain).permissions(permissions).expireDate(LocalDateUtils.dateToLocalDateTime(rawClaims.getBody().getExpiration())).build();
        //重置缓存时间
        TokenCacheUtils.expire(tokenKey, credentials.getTokenExpirationTime());
        //设置权限点
        List<GrantedAuthority> authorities = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        return new JwtAuthenticationToken(userContext, null, authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
