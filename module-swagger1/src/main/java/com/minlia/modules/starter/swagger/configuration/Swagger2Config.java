//package com.minlia.modules.starter.swagger.configuration;
//
//import com.fasterxml.classmate.TypeResolver;
//import com.minlia.modules.starter.swagger.properties.SwaggerConfigurationProperties;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StopWatch;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.ApiKey;
//import springfox.documentation.service.AuthorizationScope;
//import springfox.documentation.service.SecurityReference;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spi.service.contexts.SecurityContext;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger.web.ApiKeyVehicle;
//import springfox.documentation.swagger.web.SecurityConfiguration;
//import springfox.documentation.swagger.web.UiConfiguration;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static com.google.common.collect.Lists.newArrayList;
//
///**
// * Created by user on 11/14/15.
// */
//@Configuration
//public class Swagger2Config {
//
//    @Autowired
//    private TypeResolver typeResolver;
//
//    @Autowired
//    private SwaggerConfigurationProperties minliaProperties;
//
//    @Bean
//    public Docket documentation() {
//        StopWatch watch = new StopWatch();
//        watch.start();
//        Docket docket = new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.any())
////                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
//                .paths(PathSelectors.any())
////                .paths(PathSelectors.regex(minliaProperties.getPath()))
//                .build()
////                .directModelSubstitute(LocalDate.class, java.sql.Date.class)
////                .directModelSubstitute(LocalDateTime.class, java.util.Date.class)
//                .pathMapping("/")
////                .additionalModels(typeResolver.resolve(AdditionalModel.class))
//                .apiInfo(this.apiInfo());
//        watch.stop();
//        return docket;
//    }
//
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title(minliaProperties.getTitle())
//                .description(minliaProperties.getDescription())
//                .version(minliaProperties.getVersion())
//                .contact(minliaProperties.getContact())
//                .build();
//    }
//
////    private ApiKey apiKey() {
////        return new ApiKey("mykey", "api_key", "header");
////    }
////
////    private SecurityContext securityContext() {
////        return SecurityContext.builder()
////                .securityReferences(defaultAuth())
////                .forPaths(PathSelectors.regex("/anyPath.*"))
////                .build();
////    }
//
////    List<SecurityReference> defaultAuth() {
////        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
////        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
////        authorizationScopes[0] = authorizationScope;
////        return newArrayList(new SecurityReference("mykey", authorizationScopes));
////    }
//
//    @Bean
//    SecurityConfiguration security() {
////        return new SecurityConfiguration(
////                "test-app-client-id",
////                "test-app-client-secret",
////                "test-app-realm",
////                "test-app",
////                "apiKey",
////                ApiKeyVehicle.HEADER,
////                "api_key",
////                "," /*scope separator*/);
//        return new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER, "X-Auth-Token", ",");
//    }
//
////    @Bean
////    PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
////        return new PageableParameterBuilderPlugin(nameExtractor, resolver);
////    }
//
////    @Bean
////    UiConfiguration uiConfig() {
////        return new UiConfiguration(
////                "validatorUrl",// url
////                "none",       // docExpansion          => none | list
////                "alpha",      // apiSorter             => alpha
////                "schema",     // defaultModelRendering => schema
////                UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS,
////                false,        // enableJsonEditor      => true | false
////                true,         // showRequestHeaders    => true | false
////                60000L);      // requestTimeout => in milliseconds, defaults to null (uses jquery xh timeout)
////    }
//
//}
