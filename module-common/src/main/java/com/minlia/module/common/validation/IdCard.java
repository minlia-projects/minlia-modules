package com.minlia.module.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that check the id card
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { IdCardValidator.class })
public @interface IdCard {

    String message() default "请输入有效身份证号码";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
