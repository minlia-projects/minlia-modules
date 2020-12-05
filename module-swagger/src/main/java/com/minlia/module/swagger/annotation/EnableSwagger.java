package com.minlia.module.swagger.annotation;

import com.minlia.module.swagger.config.SwaggerConfiguration;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

/**
 * Swagger 启动注解
 *
 * @author garen
 * @version 2.0
 * @date 2020-12-2 13:41:16
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
@EnableSwagger2
@Import({SwaggerConfiguration.class})
public @interface EnableSwagger {

}