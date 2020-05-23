package com.minlia.module.common.validation;

import com.minlia.cloud.i18n.Lang;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;
import java.util.regex.Pattern;

/**
 * Annotation that check the cellphone
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { Cellphone.CellphoneValidator.class })
//@ReportAsSingleViolation
//@Pattern(regexp = "")
public @interface Cellphone {

    boolean required() default false;

    //    String message() default "{javax.validation.constraints.NotBlank.message}";
    String message() default "system.validator.message.cellphone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class CellphoneValidator implements ConstraintValidator<Cellphone, String> {

        private boolean required;

        private String message;

        @Value("${system.valid.rule.cellphone}")
        private String cellphoneRule;

        @Override
        public void initialize(Cellphone mobile) {
            this.required = mobile.required();
            this.message = mobile.message();
        }

        @Override
        public boolean isValid(String mobile, ConstraintValidatorContext ctx) {
            if (StringUtils.isNotEmpty(mobile)) {
                //禁止默认消息返回
                ctx.disableDefaultConstraintViolation();
                //自定义返回消息
                ctx.buildConstraintViolationWithTemplate(Lang.get(message)).addConstraintViolation();
                return Pattern.matches(cellphoneRule, mobile);
            } else {
                return true;
            }
        }

    }
}
