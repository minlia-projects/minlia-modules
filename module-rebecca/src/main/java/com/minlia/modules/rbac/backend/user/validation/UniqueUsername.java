package com.minlia.modules.rbac.backend.user.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation that check the uniqueness of username
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = { UniqueUsernameValidator.class })
public @interface UniqueUsername {

    String message() default "{username.already.exists}";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
