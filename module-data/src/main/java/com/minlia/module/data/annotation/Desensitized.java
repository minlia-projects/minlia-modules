//package com.minlia.module.data.annotation;
//
//import com.minlia.module.data.enumeration.SensitiveTypeEnum;
//
//import java.lang.annotation.*;
//
//@Target({ElementType.FIELD, ElementType.METHOD})
//@Retention(RetentionPolicy.RUNTIME)
//@Inherited
//@Documented
//public @interface Desensitized {
//
//    /**
//     * 脱敏类型(规则)
//     */
//    SensitiveTypeEnum type();
//
//    /**
//     * 判断注解是否生效的方法
//     */
//    String isEffictiveMethod() default "";
//
//}