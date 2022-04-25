package com.minlia.modules.security.autoconfiguration;

import com.minlia.modules.security.authentication.ajax.AjaxLoginAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.ajax.DefaultLogoutSuccessHandler;
import com.minlia.modules.security.authentication.cellphone.CellphoneCaptchaAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.JwtTokenAuthenticationProcessingFilter;
import com.minlia.modules.security.authentication.jwt.SkipPathRequestMatcher;
import com.minlia.modules.security.config.SysSecurityConfig;
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
import org.springframework.security.web.access.AccessDeniedHandler;
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
//@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    //TODO 分离开发环境与生产环境 X-Authorization, 需要同时修改 swagger项目里面Swagger2Config文件
    public static final String JWT_TOKEN_HEADER_PARAM = "X-Auth-Token";

    public static final String OPEN_ENTRY_POINT = "/api/open/**";
    public static final String LOGIN_ENTRY_POINT = "/api/auth/login";
    public static final String LOGIN_CELLPHONE_ENTRY_POINT = "/api/auth/cellphone";
    public static final String LOGOUT_ENTRY_POINT = "/api/auth/logout";
    public static final String TOKEN_REFRESH_ENTRY_POINT = "/api/auth/token";
    public static final String TOKEN_BASED_AUTH_ENTRY_POINT = "/api/v*/**";

    @Autowired
    private JwtProperty jwtProperty;
    //@Autowired
    //private SysSecurityConfig sysSecurityConfig;

    //@Autowired
    //@Qualifier("minliaAccessDeniedHandler")
    //private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    //@Qualifier("minliaAuthenticationEntryPoint")
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private DefaultLogoutSuccessHandler defaultLogoutSuccessHandler;
    @Autowired
    @Qualifier("ajaxAwareAuthenticationSuccessHandler")
    private AuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;
    @Autowired
    @Qualifier("ajaxAwareAuthenticationFailureHandler")
    private AuthenticationFailureHandler ajaxAuthenticationFailureHandler;

    @Autowired
    private AuthenticationProvider jwtAuthenticationProvider;
    @Autowired
    private AuthenticationProvider ajaxAuthenticationProvider;
    @Autowired
    private AuthenticationProvider cellphoneCaptchaAuthenticationProvider;

    @Autowired
    SessionRegistry sessionRegistry;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(jwtAuthenticationProvider);
        auth.authenticationProvider(ajaxAuthenticationProvider);
        auth.authenticationProvider(cellphoneCaptchaAuthenticationProvider);
//        auth.userDetailsService(MinliaUserDetailsService)
//        auth.inMemoryAuthentication()
//                .withUser("memuser")
//                .password(encoder().encode("pass"))
//                .roles("USER")
//                .authorities();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //禁用表单登录，前后端分离用不上
                //.formLogin().disable()
                //HTTP Strict Transport Security（通常简称为 HSTS）是一个安全功能，它告诉浏览器只能通过HTTPS访问当前资源，而不是HTTP
                .headers().httpStrictTransportSecurity().maxAgeInSeconds(Duration.ofDays(365L).getSeconds()).includeSubDomains(true)
                //X-Frame-Options HTTP 响应头是用来给浏览器指示允许一个页面可否在 <frame>, <iframe>或者 <object> 中展现的标记。网站可以使用此功能，来确保自己网站的内容没有被嵌到别人的网站中去，也从而避免了点击劫持 (clickjacking) 的攻击。
                .and().frameOptions().sameOrigin()
                //We don't need CSRF for JWT based authentication
                .and().csrf().disable()
                //异常处理器
                .exceptionHandling()
                //认证通过，但是没权限处理器
                //.accessDeniedHandler(accessDeniedHandler)
                //认证未通过，不允许访问异常处理器
                .authenticationEntryPoint(this.authenticationEntryPoint)
                //设置URL的授权
                .and().authorizeRequests()
                //需要认证的接口
                .antMatchers(LOGOUT_ENTRY_POINT, TOKEN_BASED_AUTH_ENTRY_POINT).authenticated()
                //不需要认证的接口
                .antMatchers(OPEN_ENTRY_POINT, LOGIN_CELLPHONE_ENTRY_POINT, LOGIN_ENTRY_POINT, TOKEN_REFRESH_ENTRY_POINT).permitAll()
                //.anyRequest().authenticated()
                //将TOKEN校验过滤器配置到过滤器链中，否则不生效，放到UsernamePasswordAuthenticationFilter之前
                .and()
                .addFilterBefore(jwtTokenAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(ajaxLoginAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(cellphoneCaptchaAuthenticationProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
                //退出登陆
                .logout().logoutRequestMatcher(new AntPathRequestMatcher(LOGOUT_ENTRY_POINT))
                //.addLogoutHandler(new DisableMultipleSecurityContextLogoutHandler())
                .logoutSuccessHandler(defaultLogoutSuccessHandler)
                //禁用session，JWT校验不需要session
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    AjaxLoginAuthenticationProcessingFilter ajaxLoginAuthenticationProcessingFilter() {
        AjaxLoginAuthenticationProcessingFilter filter = new AjaxLoginAuthenticationProcessingFilter(LOGIN_ENTRY_POINT);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(ajaxAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
        return filter;
    }

    @Bean
    CellphoneCaptchaAuthenticationProcessingFilter cellphoneCaptchaAuthenticationProcessingFilter() {
        CellphoneCaptchaAuthenticationProcessingFilter filter = new CellphoneCaptchaAuthenticationProcessingFilter(LOGIN_CELLPHONE_ENTRY_POINT);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(cellphoneCaptchaAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationSuccessHandler(ajaxAuthenticationSuccessHandler);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
        return filter;
    }

    //@Bean
    JwtTokenAuthenticationProcessingFilter jwtTokenAuthenticationProcessingFilter() throws Exception {
        List<String> pathsToSkip = Arrays.asList(TOKEN_REFRESH_ENTRY_POINT, LOGIN_ENTRY_POINT);
        SkipPathRequestMatcher matcher = new SkipPathRequestMatcher(pathsToSkip, TOKEN_BASED_AUTH_ENTRY_POINT);
        JwtTokenAuthenticationProcessingFilter filter = new JwtTokenAuthenticationProcessingFilter(matcher, jwtProperty);
        ProviderManager providerManager = new ProviderManager(Collections.singletonList(jwtAuthenticationProvider));
        filter.setAuthenticationManager(providerManager);
        filter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
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