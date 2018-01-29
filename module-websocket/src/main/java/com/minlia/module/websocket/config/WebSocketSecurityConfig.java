//package com.minlia.module.websocket.v1.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
///**
// * Created by garen on 2017/11/30.
// */
//@Configuration
//@EnableWebSecurity
//public class WebSocketSecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .authorizeRequests()
//                .antMatchers("/","/login").permitAll()
//                .anyRequest().authenticated()
//                .and()
//
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/chat")
////          .failureUrl("/error")错误是跳转的页面
//                .permitAll()
//                .and()
////          .rememberMe()是否被kookie记住
////              .tokenValiditySeconds(3600)记住的时间长度3600s
////              .key("key")cookie中的私钥
////          .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//                .inMemoryAuthentication()
//                .withUser("admin")
//                .password("admin")
//                .roles("USER")
//
//                .and()
//
//                .withUser("user")
//                .password("user")
//                .roles("USER");
//    }
//
//    @Override
//    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/resources/static/**");
//    }
//
//}
