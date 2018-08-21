package com.minlia.modules.starter.swagger;

import com.minlia.modules.starter.swagger.configuration.SwaggerConfig;
import com.minlia.modules.starter.swagger.properties.SwaggerConfigurationProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
//@ConditionalOnClass(SwaggerConfig.class)
//@EnableConfigurationProperties(SwaggerConfigurationProperties.class)
@Profile(value = {"dev"})
public class SwaggerAutoConfiguration {

    @EnableDevSwagger
//    @EnableSwagger2
    public static class Swagger2AutoConfig {

    }
//
//
//    @Configuration
//    @EnableWebMvc
//    public static class EnableMinliaStaticResourceConfiguration extends WebMvcConfigurerAdapter {
//        private final String[] CLASSPATH_RESOURCE_LOCATIONS = {
//                "classpath:/META-INF/resources/", "classpath:/resources/",
//                "classpath:/static/", "classpath:/public/"
//        };
//
//        @Override
//        public void addResourceHandlers(ResourceHandlerRegistry registry) {
//            registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
//        }
//    }

}
