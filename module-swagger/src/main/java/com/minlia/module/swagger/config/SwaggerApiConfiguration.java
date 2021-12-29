package com.minlia.module.swagger.config;

import com.google.common.collect.Sets;
import com.minlia.module.swagger.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerConfiguration
 *
 * @author garen
 */
@Component
public class SwaggerApiConfiguration extends SwaggerConfiguration {

    public SwaggerApiConfiguration(SwaggerProperties swaggerProperties) {
        super(swaggerProperties);
    }

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30).pathMapping("/")
                .groupName("全部")
                // 定义是否开启swagger，false为关闭，可以通过变量控制
                .enable(getSwaggerProperties().getEnable())
//                .groupName()
                // 将api的元信息设置为包含在json ResourceListing响应中。
                .apiInfo(apiInfo())
                // 接口调试地址
                .host(getSwaggerProperties().getHost())
                // 选择哪些接口作为swagger的doc发布
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build()
                .directModelSubstitute(Long.class, String.class)
                .directModelSubstitute(long.class, String.class)
                // 支持的通讯协议集合
                .protocols(Sets.newHashSet("https", "http"))
                // 授权信息设置，必要的header token等认证信息
                .securitySchemes(securitySchemes())
                // 授权信息全局应用
                .securityContexts(securityContexts());
    }

}