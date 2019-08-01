package com.minlia.modules.security.authentication.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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
public class UserDetailsAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public UserDetailsAuthenticationProcessingFilter() {
        super(new AntPathRequestMatcher("/auth/login", "GET"));
    }

//    protected UserDetailsAuthenticationProcessingFilter(String defaultFilterProcessesUrl) {
//        super(defaultFilterProcessesUrl);
//    }

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null) {
            throw new InternalAuthenticationServiceException("Failed to get the username");
        }

        if (password == null) {
            throw new InternalAuthenticationServiceException("Failed to get the password");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);


        //用户名密码验证通过后,注册session
//        sessionRegistry.registerNewSession(request.getSession().getId(), token.getPrincipal());

        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);


        //获取登录凭证：用户名、邮箱、手机号码、密码
//        LoginCredentials loginCredentials = objectMapper.readValue(request.getReader(), LoginCredentials.class);
//        this.preConditions(loginCredentials);
//        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginCredentials, loginCredentials.getPassword());
//        return this.getAuthenticationManager().authenticate(token);
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


//    /**
//     * 前置校验, 是否只传入了一组登录对象
//     *
//     * @param credentials
//     */
//    private void preConditions(LoginCredentials credentials) {
//        ApiAssert.notNull(credentials, SecurityCode.Exception.AUTH_CREDENTIALS_NOT_FOUND);
//
//        if (StringUtils.isBlank(credentials.getPassword()) && StringUtils.isBlank(credentials.getCaptcha())) {
//            throw new BadCredentialsException("");
//        }
//
//        if (StringUtils.isBlank(credentials.getAccount())) {
//            throw new AuthenticationCredentialsNotFoundException("Username or Password not provided");
//        } else {
//            MinliaValidProperties validProperties = ContextHolder.getContext().getBean(MinliaValidProperties.class);
//            if (Pattern.matches(validProperties.getCellphone(), credentials.getAccount())) {
//                credentials.setCellphone(credentials.getAccount());
//                credentials.setMethod(LoginMethodEnum.CELLPHONE);
//            } else if (Pattern.matches(validProperties.getUsername(), credentials.getAccount())) {
//                credentials.setUsername(credentials.getAccount());
//                credentials.setMethod(LoginMethodEnum.USERNAME);
//            } else if (Pattern.matches(validProperties.getEmail(), credentials.getAccount())) {
//                credentials.setEmail(credentials.getAccount());
//                credentials.setMethod(LoginMethodEnum.EMAIL);
//            } else {
//                throw new BadCredentialsException("");
//            }
//        }
//    }

}
