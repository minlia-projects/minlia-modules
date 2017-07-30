//package com.minlia.modules.starter.swagger.configuration;
//
//import com.google.common.base.Predicate;
//import com.minlia.modules.starter.swagger.properties.SwaggerConfigurationProperties;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
//import org.springframework.context.annotation.Bean;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//import springfox.documentation.builders.ApiInfoBuilder;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import static com.google.common.base.Predicates.or;
//import static springfox.documentation.builders.PathSelectors.regex;
//
//@Slf4j
////@EnableSwagger2
////@EnableConfigurationProperties(SwaggerConfigurationProperties.class)
//public class SwaggerConfiguration {
//
//  private final SwaggerConfigurationProperties properties;
//
//  public SwaggerConfiguration(final SwaggerConfigurationProperties properties) {
//    this.properties = properties;
//    log.info("using springfox.swagger2 with properties='{}'", properties);
//  }
//
//
//  @Bean
//  @ConditionalOnMissingBean
//  public Docket documentation() {
//    return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any())
//      .paths(paths()).build()
//      .directModelSubstitute(LocalDate.class, java.sql.Date.class)
//      .directModelSubstitute(LocalDateTime.class, java.util.Date.class).pathMapping("/")
//      .apiInfo(metadata());
//  }
//
////  @Bean
////  @ConditionalOnMissingBean
////  public UiConfiguration uiConfig() {
////    return UiConfiguration.DEFAULT;
////  }
//
//  private ApiInfo metadata() {
////        return new ApiInfoBuilder().title("Minlia Cloud Development Environment System API").description("Minlia Cloud Development Environment System API").version("2.0").contact("cloud@minlia.com").build();
//    return new ApiInfoBuilder().title(properties.getTitle()).description(properties.getDescription()).version(properties.getVersion()).contact(properties.getContact()).build();
//  }
//
//
////  @Bean
////  @ConditionalOnMissingBean
////  public Docket docket(final TypeResolver typeResolver) {
////    return new Docket(DocumentationType.SWAGGER_2)
////      .select()
////      .apis(RequestHandlerSelectors.any())
//////      .paths(not(regex("/error.*")))
////      .paths(paths())
////      .build()
////      .pathMapping("/")
////      .apiInfo(new ApiInfoBuilder()
////        .title(properties.getTitle())
////        .version(properties.getVersion())
////        .build()
////      );
////  }
//  private Predicate<String> paths() {
//    return or(
//      regex(properties.getPath())
//    );
//  }
//  @Bean
//  public WebMvcConfigurerAdapter forwardToIndex() {
//    return new WebMvcConfigurerAdapter() {
//      @Override
//      public void addViewControllers(ViewControllerRegistry registry) {
//        if (properties.isRedirect()) {
//          registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
//        }
//      }
//    };
//  }
//
//
//}
