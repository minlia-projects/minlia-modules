package com.minlia.modules.security.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        SecurityContextHolder.clearContext();



        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);

        Map<String, Object> tokenMap = new HashMap<>();
        tokenMap.put("token", "sadfdsa");
        tokenMap.put("expireDate", "dsafdsaa");
        tokenMap.put("refreshToken", "xcccccccccccc");

        response.getWriter().write(objectMapper.writeValueAsString(tokenMap));
//		super.onAuthenticationSuccess(request, response, authentication);


    }

}
