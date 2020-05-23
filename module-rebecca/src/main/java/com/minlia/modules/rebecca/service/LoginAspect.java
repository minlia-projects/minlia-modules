package com.minlia.modules.rebecca.service;

import com.minlia.modules.http.NetworkUtil;
import com.minlia.modules.rebecca.bean.domain.LoginLog;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * Created by garen on 2018/5/27.
 */
@Aspect
@Component
public class LoginAspect {

    @Autowired
    private LoginLogService loginLogService;

    @Pointcut("execution (* com.minlia.modules.rebecca.authentication.RbacAuthenticationService.authentication(..))")
    public void login() {
    }

//    @Pointcut("execution(@java.lang.Deprecated * *(..))")
//    public void login1(){};

    @Before("login()")
    public void beforeLogin(JoinPoint joinPoint) {
        UsernamePasswordAuthenticationToken authenticationToken = (UsernamePasswordAuthenticationToken) joinPoint.getArgs()[0];
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LoginCredentials credentials = (LoginCredentials) authenticationToken.getPrincipal();
        String username = credentials.getAccount();
        String password = credentials.getPassword();
        String ipAddress = NetworkUtil.getIpAddress(request);
        loginLogService.create(LoginLog.builder().username(username).password(password).ipAddress(ipAddress).time(LocalDateTime.now()).build());
    }

}
