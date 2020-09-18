package com.minlia.modules.security.authentication.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.property.MinliaValidProperties;
import com.minlia.modules.security.authentication.credential.LoginCredentials;
import com.minlia.modules.security.code.SecurityCode;
import com.minlia.modules.security.enumeration.LoginTypeEnum;
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
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

@Slf4j
public class AjaxLoginAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private ObjectMapper objectMapper;

    public AjaxLoginAuthenticationProcessingFilter(String defaultProcessUrl) {
        super(defaultProcessUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            if (log.isDebugEnabled()) {
                log.debug("Authentication method not supported. Request method: {}, only ajax request is supported.", request.getMethod());
            }

            //匿名登陆 TODO
//            AnonymousAuthenticationToken ret=new AnonymousAuthenticationToken();
//            org.springframework.security.authentication.AnonymousAuthenticationToken token = new org.springframework.security.authentication.AnonymousAuthenticationToken();
//            SecurityContextHolder.getContext().setAuthentication(ret);
//            return ret;
            throw new AuthMethodNotSupportedException("Authentication method not supported");
        }

        //获取登录凭证：用户名、邮箱、手机号码、密码
        LoginCredentials loginCredentials = objectMapper.readValue(request.getReader(), LoginCredentials.class);
        this.preConditions(loginCredentials);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginCredentials, loginCredentials.getPassword());
        return this.getAuthenticationManager().authenticate(token);
    }

    /**
     * 前置校验, 是否只传入了一组登录对象
     *
     * @param credentials
     */
    private void preConditions(LoginCredentials credentials) {
        ApiAssert.notNull(credentials, SecurityCode.Exception.AUTH_CREDENTIALS_NOT_FOUND);

        if (StringUtils.isBlank(credentials.getPassword()) && StringUtils.isBlank(credentials.getVcode())) {
            throw new BadCredentialsException("");
        }

        if (StringUtils.isBlank(credentials.getName())) {
            throw new AuthenticationCredentialsNotFoundException("Username or Password not provided");
        } else {
            MinliaValidProperties validProperties = ContextHolder.getContext().getBean(MinliaValidProperties.class);
            if (Pattern.matches(validProperties.getCellphone(), credentials.getName())) {
                credentials.setCellphone(credentials.getName());
                credentials.setType(LoginTypeEnum.CELLPHONE);
            } else if (Pattern.matches(validProperties.getUsername(), credentials.getName())) {
                credentials.setUsername(credentials.getName());
                credentials.setType(LoginTypeEnum.USERNAME);
            } else if (Pattern.matches(validProperties.getEmail(), credentials.getName())) {
                credentials.setEmail(credentials.getName());
                credentials.setType(LoginTypeEnum.EMAIL);
            } else {
                throw new BadCredentialsException("");
//                throw new ProviderNotFoundException("");
            }
        }
    }

//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
//        successHandler.onAuthenticationSuccess(request, response, authResult);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        SecurityContextHolder.clearContext();
//        failureHandler.onAuthenticationFailure(request, response, failed);
//    }

}