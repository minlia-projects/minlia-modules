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
@Constraint(validatedBy = { PasswordValidator.class })
public @interface Password {

    String message() default "密码长度在6-16,需包含小写字母、大写字母、数字、特殊符号的两种及以上";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
