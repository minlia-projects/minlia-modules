package com.minlia.module.riskcontrol.config;

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
public class RiskcontrolSwaggerConfig extends SwaggerConfiguration {

    public RiskcontrolSwaggerConfig(SwaggerProperties swaggerProperties) {
        super(swaggerProperties);
    }

    @Bean
    public Docket riskcontrolApi() {
        return new Docket(DocumentationType.OAS_30)
                .groupName("Risk control")
                .pathMapping("/")
                .enable(getSwaggerProperties().getEnable())
                .apiInfo(apiInfo())
                .host(getSwaggerProperties().getHost())
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.ant("/api/**/riskcontrol/**")
                        .or(PathSelectors.ant("/api/idempotent/**")))
                .build()
                .directModelSubstitute(Long.class, String.class)
                .directModelSubstitute(long.class, String.class)
                .protocols(Sets.newHashSet("https", "http"))
                .securitySchemes(securitySchemes())
                .securityContexts(securityContexts());
    }

}