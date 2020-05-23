package com.minlia.module.common.validation;

import cn.hutool.core.util.IdcardUtil;
import com.minlia.cloud.i18n.Lang;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Annotation that check the cellphone
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {HKID.HKIDValidator.class})
//@ReportAsSingleViolation
//@Pattern(regexp = "")
public @interface HKID {

    boolean required() default false;

    //    String message() default "{javax.validation.constraints.NotBlank.message}";
    String message() default "system.validator.message.cellphone";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class HKIDValidator implements ConstraintValidator<HKID, String> {

        private boolean required;

        private String message;

        @Override
        public void initialize(HKID hkid) {
            this.required = hkid.required();
            this.message = hkid.message();
        }

        @Override
        public boolean isValid(String hkid, ConstraintValidatorContext ctx) {
            if (required && StringUtils.isNotEmpty(hkid)) {
                return false;
            }

            if (StringUtils.isNotEmpty(hkid)) {
                //禁止默认消息返回
                ctx.disableDefaultConstraintViolation();
                //自定义返回消息
                ctx.buildConstraintViolationWithTemplate(Lang.get(message)).addConstraintViolation();

                return IdcardUtil.isValidHKCard(hkid);
            } else {
                return true;
            }
        }

//        public static void main(String[] args) {
//            boolean b1 = IdcardUtil.isValidHKCard("G123456(A)");
//            boolean b2 = IdcardUtil.isValidHKCard("O8629642");
//            System.out.println(b1);
//            System.out.println(b2);
//        }

    }

}
