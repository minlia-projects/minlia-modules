//package com.minlia.modules.security.authentication.cellphone;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.minlia.modules.security.body.AuthenticationErrorResponseBody;
//import com.minlia.modules.security.code.SecurityCode;
//import com.minlia.modules.security.exception.AuthMethodNotSupportedException;
//import com.minlia.modules.security.exception.DefaultAuthenticationException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.authentication.*;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.web.authentication.AuthenticationFailureHandler;
//import org.springframework.stereotype.Component;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CellphoneAuthenticationFailureHandler implements AuthenticationFailureHandler {
//
//    @Autowired
//    private ObjectMapper mapper;
//
//    @Override
//    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
//        SecurityContextHolder.clearContext();
//
//        response.setStatus(HttpStatus.OK.value());
//        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
//
//        if (e instanceof AuthMethodNotSupportedException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.AUTH_METHOD_NOT_SUPPORTED));
//        } else if (e instanceof UsernameNotFoundException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.USERNAME_NOT_FOUND));
//        } else if (e instanceof AuthenticationCredentialsNotFoundException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.AUTH_CREDENTIALS_NOT_FOUND));
//        } else if (e instanceof DisabledException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.ACCOUNT_DISABLED));
//        } else if (e instanceof LockedException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.ACCOUNT_LOCKED));
//        } else if (e instanceof AccountExpiredException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.ACCOUNT_EXPIRED));
//        } else if (e instanceof CredentialsExpiredException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.ACCOUNT_CREDENTIALS_EXPIRED));
//        } else if (e instanceof BadCredentialsException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.BAD_CREDENTIALS));
//        } else if (e instanceof AuthenticationServiceException) {
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.AUTH_SERVICE));
//        } else if (e instanceof DefaultAuthenticationException) {
//            //自定义异常
//            DefaultAuthenticationException exception = (DefaultAuthenticationException) e;
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(exception.getCode(), exception.getArgs()));
//        } else {
//            //其它异常
//            mapper.writeValue(response.getWriter(), new AuthenticationErrorResponseBody(SecurityCode.Exception.AUTH_SERVICE));
//        }
//    }
//
//}
