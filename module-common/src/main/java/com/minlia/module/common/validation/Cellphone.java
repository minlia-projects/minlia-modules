package com.minlia.module.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that check the cellphone
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { CellphoneValidator.class })
//@ReportAsSingleViolation
//@Pattern(regexp = "")
public @interface Cellphone {

    String message() default "请输入11位有效手机号码";
//    String message() default "system.validator.message.cellphone";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
