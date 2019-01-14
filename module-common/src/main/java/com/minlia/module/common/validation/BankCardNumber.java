package com.minlia.module.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that check the bank card number
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { BankCardNumberValidator.class })
public @interface BankCardNumber {

    String message() default "请输入有效银行卡号";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

}
