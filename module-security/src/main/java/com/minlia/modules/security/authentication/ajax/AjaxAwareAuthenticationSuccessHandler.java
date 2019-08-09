package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import com.minlia.cloud.body.Response;
import com.minlia.modules.security.model.UserContext;
import com.minlia.modules.security.model.token.AccessJwtToken;
import com.minlia.modules.security.model.token.JwtToken;
import com.minlia.modules.security.model.token.JwtTokenFactory;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Component
public class AjaxAwareAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper mapper;

    private final JwtTokenFactory tokenFactory;

    @Autowired
    public AjaxAwareAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory) {
        this.mapper = mapper;
        this.tokenFactory = tokenFactory;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserContext userContext = (UserContext) authentication.getPrincipal();

        String id = UUID.randomUUID().toString();
        JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext, id);
        JwtToken refreshToken = tokenFactory.createRefreshToken(userContext, id);
        JwtToken rawToken = tokenFactory.createRawJwtToken(userContext, id);

        Map<String, Object> tokenMap = Maps.newHashMap();
        tokenMap.put("token", accessToken.getToken());
        tokenMap.put("expireDate", ((AccessJwtToken) accessToken).getClaims().getExpiration());
        tokenMap.put("refreshToken", refreshToken.getToken());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //将tokenMap 转换成状态化返回体再返回
        mapper.writeValue(response.getWriter(), Response.success(tokenMap));

        clearAuthenticationAttributes(request);

        //缓存token TODO
        TokenCacheUtils.kill(userContext.getGuid());
        TokenCacheUtils.cache(userContext.getGuid(), rawToken.getToken(), tokenFactory.getSettings().getTokenExpirationTime());
        //保存信息到redis：因为存在多端登录所以用Set
//        RedisUtils.sSetAndTime(TokenRedisConstants.token + userContext.getUsername(), tokenFactory.getSettings().getTokenExpirationTime(), accessToken.getToken());
//        RedisUtils.sSetAndTime(TokenRedisConstants.r_token + userContext.getUsername(), tokenFactory.getSettings().getRefreshTokenExpTime(), refreshToken.getToken());
    }

    /**
     * Removes temporary authentication-related data which may have been stored
     * in the session during the authentication process..
     */
    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
