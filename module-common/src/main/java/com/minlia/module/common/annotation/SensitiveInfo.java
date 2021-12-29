package com.minlia.module.common.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minlia.cloud.constant.SymbolConstants;
import com.minlia.module.common.enums.SensitiveTypeEnum;
import com.minlia.module.common.util.SensitiveInfoSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 字段脱敏注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerialize.class)
public @interface SensitiveInfo {

    /**
     * 脱敏类型(规则)
     */
    SensitiveTypeEnum value() default SensitiveTypeEnum.ALL;

    int size() default 1;

    int begin() default 1;

    int end() default 1;

    /**
     * 覆盖字符
     * @return
     */
    String pad() default SymbolConstants.STAR;

}