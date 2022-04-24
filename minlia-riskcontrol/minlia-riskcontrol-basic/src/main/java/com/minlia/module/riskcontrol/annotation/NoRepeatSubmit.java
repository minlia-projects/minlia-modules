package com.minlia.module.riskcontrol.annotation;

import java.lang.annotation.*;

/**
 * 防止重复提交
 *
 * @author garen
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface NoRepeatSubmit {

    String key() default "";

}