package com.minlia.modules.starter.swagger;

import com.minlia.modules.starter.swagger.configuration.Swagger2Config;
import org.springframework.context.annotation.Import;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
@EnableSwagger2
@Import({ Swagger2Config.class})
public @interface EnableDevSwagger {
}
