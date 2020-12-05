package com.minlia.module.swagger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author garen
 */
//@EnableAutoConfiguration
@Configuration
//@ConditionalOnClass(SwaggerConfiguration.class)
//@Profile(value = {"!prod"})
public class SwaggerAutoConfiguration {

    @Configuration
    @EnableWebMvc
    public static class EnableStaticResourceConfiguration implements WebMvcConfigurer {

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

}