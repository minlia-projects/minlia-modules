package com.minlia.module.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that check the cellphone
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { NameZhValidator.class })
public @interface NameZh {

    String message() default "请输入2-6位中文姓名";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
