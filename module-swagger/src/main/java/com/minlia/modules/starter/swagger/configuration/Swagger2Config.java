package com.minlia.modules.starter.swagger.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.minlia.modules.starter.swagger.properties.SwaggerConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by user on 11/14/15.
 */
@Slf4j
public class Swagger2Config {

    @Autowired
    private TypeResolver typeResolver;

    @Autowired
    private SwaggerConfigurationProperties minliaProperties;

    @Bean
    public Docket documentation() {
        log.debug("Starting Swagger");
        StopWatch watch = new StopWatch();
        watch.start();

        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex(minliaProperties.getPath()))
                .build()
                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(LocalDateTime.class, java.util.Date.class).pathMapping("/")
                .apiInfo(apiInfo());

        watch.stop();
        log.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(minliaProperties.getTitle())
                .description(minliaProperties.getDescription())
                .version(minliaProperties.getVersion())
                .contact(new Contact(minliaProperties.getContact(), "", ""))
//                .contact(minliaProperties.getContact())
                .build();
    }

    @Bean
    SecurityConfiguration security() {
        return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "X-Auth-Token", ",");
    }

//    @Bean
//    PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver typeResolver) {
//        return new PageableParameterBuilderPlugin(nameExtractor, typeResolver);
//    }

//    @Bean
//    public UiConfiguration uiConfig() {
//        return UiConfiguration.DEFAULT;
//    }

}
