package com.minlia.modules.security.demo;

import com.minlia.modules.security.authentication.ajax.AjaxLoginAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.ajax.DefaultLogoutSuccessHandler;
import com.minlia.modules.security.authentication.filter.UserDetailsAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.SkipPathRequestMatcher;
import com.minlia.modules.security.authentication.provider.UserDetailsAuthenticationProvider;
import com.minlia.modules.security.web.filter.SystemCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
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
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true, securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO 分离开发环境与生产环境 X-Authorization, 需要同时修改 swagger项目里面Swagger2Config文件
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Auth-Token";


    public static final String OPEN_POINT = "/api/open/**";
    public static final String LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String LOGOUT_ENTRY_POINT = "/api/auth/logout";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/v*/**";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";


    @Autowired
    private AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;


    @Autowired
    private UserDetailsAuthenticationProvider userDetailsAuthenticationProvider;

    @Autowired
    private AuthenticationProvider ajaxAuthenticationProvider;

    @Autowired
    private AuthenticationProvider jwtAuthenticationProvider;


    @Autowired
    private UserDetailsAuthenticationProcessingFilter userDetailsAuthenticationProcessingFilter;

    @Autowired
    private AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter;

    @Autowired
    private JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter;


//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;

    @Autowired
    SessionRegistry sessionRegistry;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(userDetailsAuthenticationProvider);
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);

//        auth.inMemoryAuthentication()
//        .withUser("memuser")
//        .password(encoder().encode("pass"))
//        .roles("USER")
//        .authorities();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // We don't need CSRF for JWT based authentication
//                .exceptionHandling()
//                .authenticationEntryPoint(this.authenticationEntryPoint)
//                .and()
                .authorizeRequests()
                .antMatchers(LOGOUT_ENTRY_POINT, TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()      //需要认证的URL
                .antMatchers("/auth/login").permitAll()  //不需要认证的URL
                .anyRequest().authenticated()                                                       //其它URL都需要认证
                .and()
                .addFilterBefore(new SystemCorsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(userDetailsAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ajaxLoginAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENTRY_POINT))
//                .addLogoutHandler(new DisableMultipleSecurityContextLogoutHandler())
                .logoutSuccessHandler(defaultLogoutSuccessHandler)
                .and()

//                .sessionManagement()
//                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true).sessionRegistry(getSessionRegistry())

                .sessionManagement()
                .maximumSessions(1);
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(getSessionRegistry())
//                .expiredSessionStrategy(new SessionInformationExpiredStrategyImpl());
        http.httpBasic();
    }

    @Bean
    UserDetailsAuthenticationProcessingFilter userDetailsAuthenticationProcessingFilter() {
        UserDetailsAuthenticationProcessingFilter filter = new UserDetailsAuthenticationProcessingFilter();
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(userDetailsAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
//        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }

    @Bean
    AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter() {
        AjaxLoginAuthenticationProcessingFilter filter = new AjaxLoginAuthenticationProcessingFilter(LOGIN_ENTRY_POINT);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(userDetailsAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
//        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }

    @Bean
    JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher);

//        filter.setAuthenticationManager(this.authenticationManager);

        ProviderManager providerManager = new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
//        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
    }

}
