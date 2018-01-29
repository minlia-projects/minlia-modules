package com.minlia.modules.security.autoconfiguration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.minlia.modules.security.authentication.ajax.AjaxLoginAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.ajax.DefaultLogoutSuccessHandler;
import com.minlia.modules.security.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.SkipPathRequestMatcher;
import com.minlia.modules.security.authentication.jwt.extractor.TokenExtractor;
import com.minlia.modules.security.web.RestAuthenticationEntryPoint;
import com.minlia.modules.security.web.filter.SystemCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO 分离开发环境与生产环境 X-Authorization, 需要同时修改 swagger项目里面Swagger2Config文件
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Auth-Token";
    //    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";
    public static final String OPENID_TOKEN_HEADER_PARAM = "X-WECHAT-OPEN-ID";
    public static final String FORM_BASED_LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String WECHAT_OPENID_LOGIN_ENTRY_POINT = "/api/auth/openid/wechat/login";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/v1/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    public static final String LOGOUT_ENTRY_POINT = "/api/auth/logout";

    @Autowired
    private RestAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private AuthenticationSuccessHandler successHandler;

    @Autowired
    private AuthenticationFailureHandler failureHandler;

    @Autowired
    @Qualifier(value = "ajaxAuthenticationProvider")
    private AuthenticationProvider ajaxAuthenticationProvider;

    @Autowired
    @Qualifier("jwtAuthenticationProvider")
    private AuthenticationProvider jwtAuthenticationProvider;

    @Autowired
    private TokenExtractor tokenExtractor;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DefaultLogoutSuccessHandler logoutSuccess;

    protected AjaxLoginAuthenticationProcessingFilter buildAjaxLoginProcessingFilter() throws Exception {
        AjaxLoginAuthenticationProcessingFilter filter = new AjaxLoginAuthenticationProcessingFilter(FORM_BASED_LOGIN_ENTRY_POINT, successHandler, failureHandler, objectMapper);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }

    protected JwtTokenAuthenticationProcessingFilter buildJwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, FORM_BASED_LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter
                = new JwtTokenAuthenticationProcessingFilter(failureHandler, tokenExtractor, matcher);
        filter.setAuthenticationManager(this.authenticationManager);
        return filter;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeRequests()
                .antMatchers(FORM_BASED_LOGIN_ENTRY_POINT).permitAll() // Login end-point
                .antMatchers(WECHAT_OPENID_LOGIN_ENTRY_POINT).permitAll() // Wechat Openid Login end-point
                .antMatchers(LOGOUT_ENTRY_POINT).permitAll() // Logout end-point
                .antMatchers(TOKEN_REFRESH_ENTRY_POINT).permitAll() // Token refresh end-point
                .antMatchers(LOGOUT_ENTRY_POINT).permitAll() // Logout end-point
                .and()
                .authorizeRequests()
                .antMatchers(TOKEN_BASED_AUTH_ENTRY_POINT).authenticated() // Protected API End-points
                .and()
                .addFilterBefore(new SystemCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildAjaxLoginProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(buildJwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENTRY_POINT))
                .logoutSuccessHandler(logoutSuccess);
    }
}
