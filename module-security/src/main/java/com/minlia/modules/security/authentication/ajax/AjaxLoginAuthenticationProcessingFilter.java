package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.security.authentication.credential.LoginCredential;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.code.SecurityApiCode;
import com.minlia.modules.security.exception.AuthMethodNotSupportedException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {
    private static Logger logger = LoggerFactory.getLogger(AjaxLoginAuthenticationProcessingFilter.class);

    private final AuthenticationSuccessHandler successHandler;
    private final AuthenticationFailureHandler failureHandler;

    private final ObjectMapper objectMapper;

    public AjaxLoginAuthenticationProcessingFilter(String defaultProcessUrl, AuthenticationSuccessHandler successHandler,
                                                   AuthenticationFailureHandler failureHandler, ObjectMapper mapper) {
        super(defaultProcessUrl);
        this.successHandler = successHandler;
        this.failureHandler = failureHandler;
        this.objectMapper = mapper;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if (logger.isDebugEnabled()) {
                logger.debug("Authentication method not supported. Request method: {}, only ajax request is supported.", request.getMethod());
            }
//            ApiPreconditions.is(true, ApiCode.UNSUPPORTED_REQUEST_METHOD);
//            AnonymousAuthenticationToken ret=new AnonymousAuthenticationToken();
//            SecurityContextHolder.getContext().setAuthentication(ret);
//            return ret;
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        //获取登录凭证：用户名、邮箱、手机号码、密码
        LoginCredentials credential = objectMapper.readValue(request.getReader(), LoginCredentials.class);

        LoginCredential loginCredential=convertToLoginCredential(credential);
        preConditions(loginCredential);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginCredential.getLogin(), loginCredential.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    private LoginCredential convertToLoginCredential(LoginCredentials credential) {
        ApiPreconditions.checkNotNull(credential, SecurityApiCode.NOT_NULL);

        if(!StringUtils.isEmpty(credential.getCellphone())){
            return new LoginCredential(credential.getCellphone(),credential.getPassword());
        }

        if(!StringUtils.isEmpty(credential.getEmail())){
            return new LoginCredential(credential.getEmail(),credential.getPassword());
        }

        if(!StringUtils.isEmpty(credential.getUsername())){
            return new LoginCredential(credential.getUsername(),credential.getPassword());
        }
        return new LoginCredential();
    }

    /**
     * 前置校验, 是否只传入了一组登录对象
     * @param credential
     */
    private void preConditions(LoginCredential credential){
        if (StringUtils.isBlank(credential.getLogin()) || StringUtils.isBlank(credential.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
//            SecurityContextHolder.getContext().setAuthentication(new AnonymousAuthenticationToken());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        successHandler.onAuthenticationSuccess(request, response, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
