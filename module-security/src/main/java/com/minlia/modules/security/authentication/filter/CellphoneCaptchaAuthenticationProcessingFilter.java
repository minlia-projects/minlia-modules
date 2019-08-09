package com.minlia.modules.security.authentication.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.HttpRequestMethodNotSupportedException;
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
public class CellphoneCaptchaAuthenticationProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private boolean allowOnlyPost;

    public CellphoneCaptchaAuthenticationProcessingFilter() {
        this("/auth/login/cellphone", RequestMethod.GET);
    }

    public CellphoneCaptchaAuthenticationProcessingFilter(String path, RequestMethod requestMethod) {
        super(new AntPathRequestMatcher(path, requestMethod.name()));
        this.allowOnlyPost = false;
    }

    @Autowired
    private SessionRegistry sessionRegistry;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if (this.allowOnlyPost && !RequestMethod.POST.name().equalsIgnoreCase(request.getMethod())) {
            throw new HttpRequestMethodNotSupportedException(request.getMethod(), new String[]{RequestMethod.POST.name()});
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                return authentication;
            } else if (username == null) {
                throw new BadCredentialsException("No client credentials presented");
            } else {
                if (password == null) {
                    password = "";
                }

                username = username.trim();
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, password);

                //用户名密码验证通过后,注册session
                sessionRegistry.registerNewSession(request.getSession().getId(), token.getPrincipal());
                token.setDetails(authenticationDetailsSource.buildDetails(request));
                return this.getAuthenticationManager().authenticate(token);
            }
        }
    }

}
