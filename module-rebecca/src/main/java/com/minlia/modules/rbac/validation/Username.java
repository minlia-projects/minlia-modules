package com.minlia.modules.rbac.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that check the cellphone
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { UsernameValidator.class })
public @interface Username {

    String message() default "请输入有效的用户名";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
