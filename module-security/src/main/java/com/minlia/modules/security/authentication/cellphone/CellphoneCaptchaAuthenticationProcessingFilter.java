package com.minlia.modules.security.authentication.cellphone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.exception.AuthMethodNotSupportedException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/7/31 5:57 PM
 */
@Slf4j
public class CellphoneCaptchaAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private boolean allowOnlyPost = true;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private SessionRegistry sessionRegistry;

    public CellphoneCaptchaAuthenticationProcessingFilter(String defaultProcessUrl) {
        super(defaultProcessUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("Authentication by cellphone verification code. Request method: {}.", request.getMethod());
        if (this.allowOnlyPost && !RequestMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
                return null;
            } else {
                if (log.isInfoEnabled()) {
                    log.info("Authentication method not supported. Request method: {}, only ajax request is supported.", request.getMethod());
                }
                //throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[]{RequestMethod.POST.name()});
                throw new AuthMethodNotSupportedException("Authentication method not supported");
            }
        } else {
            CellphoneLoginCredentials loginCredentials = objectMapper.readValue(request.getReader(), CellphoneLoginCredentials.class);
            this.preConditions(loginCredentials);
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginCredentials, loginCredentials.getVcode());
            return this.getAuthenticationManager().authenticate(token);
        }
    }
    //@Override
    //public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
    //    log.info("Authentication by cellphone verification code. Request method: {}.", request.getMethod());
    //    if (this.allowOnlyPost && !RequestMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
    //        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
    //            return null;
    //        } else {
    //            if (log.isInfoEnabled()) {
    //                log.info("Authentication method not supported. Request method: {}, only ajax request is supported.", request.getMethod());
    //            }
    //            //throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[]{RequestMethod.POST.name()});
    //            throw new AuthMethodNotSupportedException("Authentication method not supported");
    //        }
    //    } else {
    //        String username = request.getParameter("username");
    //        String password = request.getParameter("password");
    //        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    //        if (authentication != null && authentication.isAuthenticated()) {
    //            return authentication;
    //        } else if (username == null) {
    //            throw new BadCredentialsException("No client credentials presented");
    //        } else {
    //            if (password == null) {
    //                password = "";
    //            }
    //
    //            username = username.trim();
    //            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);
    //
    //            //用户名密码验证通过后,注册session
    //            sessionRegistry.registerNewSession(request.getSession().getId(), token.getPrincipal());
    //            token.setDetails(authenticationDetailsSource.buildDetails(request));
    //            return this.getAuthenticationManager().authenticate(token);
    //        }
    //    }
    //}

    /**
     * 前置校验, 是否只传入了一组登录对象
     *
     * @param credentials
     */
    private void preConditions(CellphoneLoginCredentials credentials) {
        ApiAssert.notNull(credentials, SecurityCode.Exception.AUTH_CREDENTIALS_NOT_FOUND);

        if (StringUtils.isBlank(credentials.getCellphone()) || StringUtils.isBlank(credentials.getVcode())) {
            throw new BadCredentialsException("");
        }

        if (StringUtils.isBlank(credentials.getCellphone())) {
            throw new AuthenticationCredentialsNotFoundException("Username or Password not provided");
        } else {

        }
    }

}