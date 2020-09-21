package com.minlia.modules.security.autoconfiguration;

import com.minlia.modules.security.authentication.ajax.AjaxLoginAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.ajax.DefaultLogoutSuccessHandler;
import com.minlia.modules.security.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.SkipPathRequestMatcher;
import com.minlia.modules.security.web.filter.SystemCorsFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.time.Duration;
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
    public static final String OPEN_V_POINT = "/api/v*/open/**";

    public static final String LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String LOGOUT_ENTRY_POINT = "/api/auth/logout";

    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/v*/**";

    @Autowired
<<<<<<< HEAD
    private AuthenticationEntryPoint authenticationEntryPoint;  // 未登陆时返回 JSON 格式的数据给前端（否则为 html）
//    AjaxAuthenticationEntryPoint authenticationEntryPoint;


    @Autowired
    @Qualifier("ajaxAwareAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

    @Autowired
    @Qualifier("ajaxAwareAuthenticationFailureHandler")
    private AuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;

//    @Autowired
//    private CellphoneAuthenticationSuccessHandler cellphoneAuthenticationSuccessHandler;
//
//    @Autowired
//    private CellphoneAuthenticationFailureHandler cellphoneAuthenticationFailureHandler;


//    @Autowired
//    private CellphoneCaptchaAuthenticationProvider cellphoneCaptchaAuthenticationProvider;
//
//    @Autowired
//    private UserDetailsAuthenticationProvider userDetailsAuthenticationProvider;

=======
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    @Qualifier("ajaxAwareAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
    @Autowired
    @Qualifier("ajaxAwareAuthenticationFailureHandler")
    private AuthenticationFailureHandler ajaxAuthenticationFailureHandler;
    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;
>>>>>>> dev/garen
    @Autowired
    private AuthenticationProvider ajaxAuthenticationProvider;
    @Autowired
    private AuthenticationProvider jwtAuthenticationProvider;
//    @Autowired
//    private AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter;
//    @Autowired
//    private JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter;

<<<<<<< HEAD

//    @Autowired
//    private CellphoneCaptchaAuthenticationProcessingFilter cellphoneCaptchaAuthenticationProcessingFilter;
//
//    @Autowired
//    private UserDetailsAuthenticationProcessingFilter userDetailsAuthenticationProcessingFilter;

    @Autowired
    private AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter;

    @Autowired
    private JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter;


    @Autowired
    SessionRegistry sessionRegistry;

//    @Autowired
//    @Qualifier("authenticationManagerBean")
//    private AuthenticationManager authenticationManager;
=======
>>>>>>> dev/garen

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    protected void configure(AuthenticationManagerBuilder auth) {
<<<<<<< HEAD
//        auth.authenticationProvider(cellphoneCaptchaAuthenticationProvider);
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(jwtAuthenticationProvider);

//        auth.userDetailsService(MinliaUserDetailsService)

//        auth.inMemoryAuthentication()
//                .withUser("memuser")
//                .password(encoder().encode("pass"))
//                .roles("USER")
//                .authorities();
=======
        auth.authenticationProvider(this.ajaxAuthenticationProvider);
        auth.authenticationProvider(this.jwtAuthenticationProvider);
>>>>>>> dev/garen
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().httpStrictTransportSecurity().maxAgeInSeconds(Duration.ofDays(365L).getSeconds()).includeSubDomains(true).and()  //HTTP Strict Transport Security（通常简称为 HSTS）是一个安全功能，它告诉浏览器只能通过HTTPS访问当前资源，而不是HTTP
                .frameOptions().sameOrigin()    //X-Frame-Options HTTP 响应头是用来给浏览器指示允许一个页面可否在 <frame>, <iframe>或者 <object> 中展现的标记。网站可以使用此功能，来确保自己网站的内容没有被嵌到别人的网站中去，也从而避免了点击劫持 (clickjacking) 的攻击。

                .and().csrf().disable() // We don't need CSRF for JWT based authentication
                .exceptionHandling()
                .authenticationEntryPoint(this.authenticationEntryPoint)
                .and()
                .authorizeRequests()
                .antMatchers(LOGOUT_ENTRY_POINT, TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()
                .antMatchers(OPEN_POINT, OPEN_V_POINT, LOGIN_ENTRY_POINT, TOKEN_REFRESH_ENTRY_POINT).permitAll()
//                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new SystemCorsFilter(), UsernamePasswordAuthenticationFilter.class)
<<<<<<< HEAD
//                .addFilterBefore(cellphoneCaptchaAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(userDetailsAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ajaxLoginAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)

                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENTRY_POINT))
//                .addLogoutHandler(new DisableMultipleSecurityContextLogoutHandler())
                .logoutSuccessHandler(defaultLogoutSuccessHandler)

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)     //不使用session
//                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .maximumSessions(1)
//                .maxSessionsPreventsLogin(true)
//                .sessionRegistry(sessionRegistry)
        ;
    }

//    @Bean
//    UserDetailsAuthenticationProcessingFilter userDetailsAuthenticationProcessingFilter() {
//        UserDetailsAuthenticationProcessingFilter filter = new UserDetailsAuthenticationProcessingFilter();
//        ProviderManager providerManager = new ProviderManager(Collections.singletonList(userDetailsAuthenticationProvider));
//        filter.setAuthenticationManager(providerManager);
//        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
////        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
//        return filter;
//    }

    @Bean
    AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter() {
        AjaxLoginAuthenticationProcessingFilter filter = new AjaxLoginAuthenticationProcessingFilter(LOGIN_ENTRY_POINT);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(ajaxAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
//        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }

//    @Bean
//    CellphoneCaptchaAuthenticationProcessingFilter cellphoneCapachaAuthenticationProcessingFilter() {
//        CellphoneCaptchaAuthenticationProcessingFilter filter = new CellphoneCaptchaAuthenticationProcessingFilter();
//        ProviderManager providerManager = new ProviderManager(Collections.singletonList(cellphoneCaptchaAuthenticationProvider));
//        filter.setAuthenticationManager(providerManager);
//        filter.setAuthenticationSuccessHandler(cellphoneAuthenticationSuccessHandler);
//        filter.setAuthenticationFailureHandler(cellphoneAuthenticationFailureHandler);
////        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
//        return filter;
//    }

    @Bean
    JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher);

//        filter.setAuthenticationManager(this.authenticationManager);

        ProviderManager providerManager = new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
//        filter.setSessionAuthenticationStrategy(new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry));
        return filter;
    }


//    @Bean(name = "minliaUserDetailsService")
//    @Override
//    public UserDetailsService userDetailsServiceBean() {
//        return new MinliaUserDetailsService();
//    }

    @Bean
    public SessionRegistry getSessionRegistry() {
        SessionRegistry sessionRegistry = new SessionRegistryImpl();
        return sessionRegistry;
    }

    @Bean
    public ServletListenerRegistrationBean httpSessionEventPublisher() {
        return new ServletListenerRegistrationBean(new HttpSessionEventPublisher());
=======
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
>>>>>>> dev/garen
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
