package com.minlia.module.data.annotation;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.module.data.enumeration.SensitiveTypeEnum;
import com.minlia.module.data.util.SensitiveInfoSerialize;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = SensitiveInfoSerialize.class)
public @interface SensitiveInfo {

    SensitiveTypeEnum value() default SensitiveTypeEnum.ALL;

    int size() default 1;

    int begin() default 1;

    int end() default 1;

    String pad() default SymbolConstants.STAR;

}