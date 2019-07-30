package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.modules.security.authentication.jwt.extractor.JwtHeaderTokenExtractor;
import com.minlia.modules.security.autoconfiguration.JwtProperty;
import com.minlia.modules.security.autoconfiguration.WebSecurityConfig;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import com.minlia.modules.security.model.token.RawAccessJwtToken;
import com.minlia.modules.security.model.token.TokenCacheUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private JwtProperty jwtProperty;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, String> result = new HashMap<>();

        String tokenPayload = request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM);
        JwtHeaderTokenExtractor tokenExtractor = new JwtHeaderTokenExtractor();
        String token = tokenExtractor.extract(tokenPayload);
        RawAccessJwtToken rawAccessToken = new RawAccessJwtToken(token);
        Jws<Claims> jwsClaims = rawAccessToken.parseClaimsWithCache(jwtProperty.getTokenSigningKey());

        TokenCacheUtils.kill(jwsClaims.getBody().get("guid", String.class));

        result.put("result", "success");
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(result));
        response.setStatus(HttpServletResponse.SC_OK);
    }

}