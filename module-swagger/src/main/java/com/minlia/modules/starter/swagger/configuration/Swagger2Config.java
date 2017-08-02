package com.minlia.modules.starter.swagger.configuration;

import com.fasterxml.classmate.TypeResolver;
import com.minlia.modules.starter.swagger.plugins.PageableParameterBuilderPlugin;
import com.minlia.modules.starter.swagger.properties.SwaggerConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.TypeNameExtractor;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by user on 11/14/15.
 */
public class Swagger2Config {

  @Autowired
  private TypeResolver typeResolver;

  @Autowired
  private SwaggerConfigurationProperties minliaProperties;

  //    @Bean
//    public Docket documentation() {
//        return new Docket(DocumentationType.SPRING_WEB).select().paths(PathSelectors.regex("/api/.*")).build().apiInfo(metadata());
//    }
  @Bean
  public Docket documentation() {
    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.regex(minliaProperties.getPath())).build().directModelSubstitute(LocalDate.class, java.sql.Date.class)
      .directModelSubstitute(LocalDateTime.class, java.util.Date.class).pathMapping("/").apiInfo(metadata())

//      .securitySchemes(securitySchemes())
//      .securityContexts(securityContexts())
//
      ;
  }


  @Bean
  PageableParameterBuilderPlugin pageableParameterBuilderPlugin(TypeNameExtractor nameExtractor, TypeResolver resolver) {
    return new PageableParameterBuilderPlugin(nameExtractor, resolver);
  }

  @Bean
  public UiConfiguration uiConfig() {
    return UiConfiguration.DEFAULT;
  }

  private ApiInfo metadata() {
//        return new ApiInfoBuilder().title("Minlia Cloud Development Environment System API").description("Minlia Cloud Development Environment System API").version("2.0").contact("cloud@minlia.com").build();
//        return new ApiInfoBuilder().title(minliaProperties.getSwagger().getTitle()).description(minliaProperties.getSwagger().getDescription()).version(minliaProperties.getSwagger().getVersion()).contact(minliaProperties.getSwagger().getContact()).build();
    return new ApiInfoBuilder().title(minliaProperties.getTitle()).description(minliaProperties.getDescription()).version(minliaProperties.getVersion()).contact(minliaProperties.getContact()).build();
  }





//  private List<? extends SecurityScheme> securitySchemes() {
//    List<SecurityScheme> authorizationTypes = Arrays.asList(new ApiKey("api_key", "api_key", "header"));
//    return authorizationTypes;
//  }
//
//  private List<SecurityContext> securityContexts() {
//    List<SecurityContext> securityContexts   = Arrays.asList(SecurityContext.builder().forPaths(Predicates.not(PathSelectors.regex("^(/error.*|/api/auth/login)$"))).securityReferences(securityReferences()).build());
//    return securityContexts;
//  }
//
//  private List<SecurityReference> securityReferences() {
//    List<SecurityReference> securityReferences = Arrays.asList(SecurityReference.builder().reference("token").scopes(new AuthorizationScope[0]).build());
//    return securityReferences;
//  }
}
