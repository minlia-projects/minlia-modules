//package com.minlia.modules.security.authentication.handler;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.common.collect.Maps;
//import com.minlia.cloud.body.Response;
//import com.minlia.modules.security.model.SysUser;
//import com.minlia.modules.security.model.token.AccessJwtToken;
//import com.minlia.modules.security.model.token.JwtToken;
//import com.minlia.modules.security.model.token.JwtTokenFactory;
//import com.minlia.modules.security.model.token.TokenCacheUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.WebAttributes;
//import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//import java.util.Map;
//
//@Component
//public class CellphoneAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Autowired
//    private JwtTokenFactory tokenFactory;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//
//        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
//
//        SysUser sysUser = (SysUser) authentication.getPrincipal();
//        JwtToken accessToken = tokenFactory.createAccessToken(sysUser.getUid());
//        JwtToken refreshToken = tokenFactory.createRefreshToken(sysUser.getUid());
//        JwtToken originalToken = tokenFactory.createRawJwtToken(null);  //TODO
//
//        Map<String, Object> tokenMap = Maps.newHashMap();
//        tokenMap.put("token", accessToken.getToken());
//        tokenMap.put("refreshToken", refreshToken.getToken());
//        tokenMap.put("expireDate", ((AccessJwtToken) accessToken).getClaims().getExpiration());
//
//        response.setStatus(HttpStatus.OK.value());
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        //将tSecurityContextHolder1okenMap 转换成状态化返回体再返回
//        mapper.writeValue(response.getWriter(), Response.success(tokenMap));
//
//        clearAuthenticationAttributes(request);
//
//        //杀掉已登陆token
//        TokenCacheUtils.kill(sysUser.getUid());
//        //缓存原始token
//        TokenCacheUtils.cache(sysUser.getUid(), originalToken.getToken(), tokenFactory.getSettings().getTokenExpirationTime());
//    }
//
//    /**
//     * Removes temporary authentication-related data which may have been stored
//     * in the session during the authentication process..
//     */
//    protected final void clearAuthenticationAttributes(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return;
//        }
//        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
//    }
//
//}
