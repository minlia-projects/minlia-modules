package com.minlia.module.riskcontrol.config;

import com.minlia.module.riskcontrol.interceptor.ApiIdempotentInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author garen
 */
@Configuration
public class ApiIdempotentConfiguration implements WebMvcConfigurer {

    @Resource
    private ApiIdempotentInterceptor apiIdempotentInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiIdempotentInterceptor).addPathPatterns("/**");
    }

}