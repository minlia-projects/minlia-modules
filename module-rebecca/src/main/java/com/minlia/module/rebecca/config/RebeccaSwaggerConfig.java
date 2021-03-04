package com.minlia.module.rebecca.config;

import com.google.common.base.Predicates;
import com.google.common.collect.Sets;
import com.minlia.module.swagger.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

/**
 * @author garen
 */
@Configuration
@RequiredArgsConstructor
public class RebeccaSwaggerConfig {

    private final SwaggerProperties swaggerProperties;

    @Bean
    public Docket webApiRebecca() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Rebecca")
                .pathMapping("/")
                .enable(swaggerProperties.getEnable())
                .apiInfo(apiInfo())
                .host(swaggerProperties.getHost())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.ant("/api/v1/menu/**")
                        .or(PathSelectors.ant("/api/**/navigation/**"))
                        .or(PathSelectors.ant("/api/**/role/**"))
                        .or(PathSelectors.ant("/api/**/user/**"))
                        .or(PathSelectors.ant("/api/**/permission/**"))
                        .or(PathSelectors.ant("/api/**/org/**"))
                        .or(PathSelectors.ant("/api/**/data/permission/**")))
                .build()
                .protocols(Sets.newHashSet("https", "http"))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

    /**
     * API 页面上半部分展示信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(swaggerProperties.getTitle() + " Api Doc")
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.contact.getName(), swaggerProperties.contact.getUrl(), swaggerProperties.contact.getEmail()))
                .version("Application Version: " + swaggerProperties.getVersion() + ", Spring Boot Version: " + SpringBootVersion.getVersion())
                .build();
    }

    /**
     * 设置授权信息
     */
    private List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("X-Auth-Token", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("X-Auth-Token", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

}