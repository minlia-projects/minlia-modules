package com.minlia.module.swagger.config;

import com.minlia.module.swagger.properties.SwaggerProperties;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.boot.SpringBootVersion;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;

/**
 * SwaggerConfiguration
 *
 * @author garen
 */
@EnableOpenApi
@Configuration
@AllArgsConstructor
@Getter
public class SwaggerConfiguration implements WebMvcConfigurer {

    private final SwaggerProperties swaggerProperties;

    /**
     * API 页面上半部分展示信息
     */
    public ApiInfo apiInfo() {
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
    public List<SecurityScheme> securitySchemes() {
        ApiKey apiKey = new ApiKey("X-Auth-Token", "token", In.HEADER.toValue());
        return Collections.singletonList(apiKey);
    }

    /**
     * 授权信息全局应用
     */
    public List<SecurityContext> securityContexts() {
        return Collections.singletonList(
                SecurityContext.builder()
                        .securityReferences(Collections.singletonList(new SecurityReference("X-Auth-Token", new AuthorizationScope[]{new AuthorizationScope("global", "")})))
                        .build()
        );
    }

    /**
     * 通用拦截器排除swagger设置，所有拦截器都会自动加swagger相关的资源排除信息
     *
     * @param registry xx
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        Field registrationsField = FieldUtils.getField(InterceptorRegistry.class, "registrations", true);
        List<InterceptorRegistration> registrations = (List<InterceptorRegistration>) ReflectionUtils.getField(registrationsField, registry);
        if (registrations != null) {
            for (InterceptorRegistration interceptorRegistration : registrations) {
                interceptorRegistration
                        .excludePathPatterns("/swagger**/**")
                        .excludePathPatterns("/webjars/**")
                        .excludePathPatterns("/v3/**")
                        .excludePathPatterns("/doc.html");
            }
        }
    }

    private final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/",
            "classpath:/resources/",
            "classpath:/static/",
            "classpath:/public/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

}