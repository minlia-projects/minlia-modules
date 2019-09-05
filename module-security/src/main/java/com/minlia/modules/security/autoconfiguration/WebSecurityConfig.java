package com.minlia.modules.security.autoconfiguration;

import com.minlia.modules.security.authentication.ajax.AjaxLoginAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.ajax.DefaultLogoutSuccessHandler;
import com.minlia.modules.security.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.SkipPathRequestMatcher;
import com.minlia.modules.security.web.filter.SystemCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO 分离开发环境与生产环境 X-Authorization, 需要同时修改 swagger项目里面Swagger2Config文件
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Auth-Token";
    //    public static final String JWT_TOKEN_HEADER_PARAM = "X-Authorization";

    public static final String OPEN_POINT = "/api/open/**";
    public static final String LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";

    public static final String LOGOUT_ENTRY_POINT = "/api/auth/logout";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/v*/**";

    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    @Qualifier("ajaxAwareAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
    @Autowired
    @Qualifier("ajaxAwareAuthenticationFailureHandler")
    private AuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;
    @Autowired
    private AuthenticationProvider ajaxAuthenticationProvider;
    @Autowired
    private AuthenticationProvider jwtAuthenticationProvider;
//    @Autowired
//    private AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter;
//    @Autowired
//    private JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter;


    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(this.ajaxAuthenticationProvider);
        auth.authenticationProvider(this.jwtAuthenticationProvider);
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
                .antMatchers(LOGOUT_ENTRY_POINT, TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()
                .antMatchers(OPEN_POINT, LOGIN_ENTRY_POINT, TOKEN_REFRESH_ENTRY_POINT).permitAll()
//                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new SystemCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ajaxLoginAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENTRY_POINT))
                .logoutSuccessHandler(defaultLogoutSuccessHandler);
    }

    @Bean
    AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter() {
        AjaxLoginAuthenticationProcessingFilter filter = new AjaxLoginAuthenticationProcessingFilter(LOGIN_ENTRY_POINT);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(this.ajaxAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher);

        ProviderManager providerManager = new ProviderManager(Collections.singletonList(this.jwtAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationFailureHandler(this.ajaxAuthenticationFailureHandler);
        return filter;
    }


}
