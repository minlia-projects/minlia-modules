package com.minlia.module.data.annotation;

import com.minlia.module.data.enumeration.CryptTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 加密字段注解
 *
 * @author wangzhuhua
 * @date 2018/09/04 下午5:19
 **/
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CryptField {

    CryptTypeEnum value() default CryptTypeEnum.AES;

}