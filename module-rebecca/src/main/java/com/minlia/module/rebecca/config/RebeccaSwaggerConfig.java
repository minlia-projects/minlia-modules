package com.minlia.module.rebecca.config;

import com.google.common.collect.Sets;
import com.minlia.module.swagger.config.SwaggerConfiguration;
import com.minlia.module.swagger.properties.SwaggerProperties;
import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author garen
 */
@Component
public class RebeccaSwaggerConfig extends SwaggerConfiguration {

    public RebeccaSwaggerConfig(SwaggerProperties swaggerProperties) {
        super(swaggerProperties);
    }

    @Bean
    public Docket webApiRebecca() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("RBAC")
                .pathMapping("/")
                .enable(getSwaggerProperties().getEnable())
                .apiInfo(apiInfo())
                .host(getSwaggerProperties().getHost())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.ant("/api/auth/**")
                        .or(PathSelectors.ant("/api/v1/auth/**"))
                        .or(PathSelectors.ant("/api/**/menu/**"))
                        .or(PathSelectors.ant("/api/**/navigation/**"))
                        .or(PathSelectors.ant("/api/**/role/**"))
                        .or(PathSelectors.ant("/api/**/user/**"))
                        .or(PathSelectors.ant("/api/**/permission/**"))
                        .or(PathSelectors.ant("/api/**/org/**"))
                        .or(PathSelectors.ant("/api/**/data/permission/**")))
                .build()
                .directModelSubstitute(Long.class, String.class)
                .directModelSubstitute(long.class, String.class)
                .protocols(Sets.newHashSet("https", "http"))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

}