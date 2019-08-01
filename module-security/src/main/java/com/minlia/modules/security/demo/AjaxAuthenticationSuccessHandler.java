package com.minlia.modules.security.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private ObjectMapper mapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", "sadfdsa");
        tokenMap.put("expireDate", System.currentTimeMillis());
        tokenMap.put("refreshToken", "xcccccccccccc");

        tokenMap.put("session_id", request.getSession().getId());

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        //将tSecurityContextHolder1okenMap 转换成状态化返回体再返回
        mapper.writeValue(response.getWriter(), tokenMap);
    }

}
