package com.minlia.module.data.annotation;

import com.minlia.module.data.enumeration.CryptTypeEnum;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface CryptField {

    CryptTypeEnum value() default CryptTypeEnum.AES;

    Class<? extends Enum> enumClass() default Enum.class;

}