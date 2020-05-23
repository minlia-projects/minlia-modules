package com.minlia.modules.security.authentication.username;

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
        sessionRegistry.registerNewSession(request.getSession().getId(), token.getPrincipal());

        token.setDetails(authenticationDetailsSource.buildDetails(request));
        return this.getAuthenticationManager().authenticate(token);
    }

}
