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
 *
 * @author garen
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {Password.PasswordValidator.class})
public @interface Password {

    //    String message() default "密码长度在6-16,需包含小写字母、大写字母、数字、特殊符号的两种及以上";
    String message() default "system.validator.message.password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * Annotation that check the password
     *
     * @author garen
     * @date 2017/6/30
     */
    class PasswordValidator implements ConstraintValidator<Password, String> {

        @Value("${system.valid.rule.password}")
        private String regex;

        private String message;

        @Override
        public void initialize(Password password) {
            this.message = password.message();
        }

        @Override
        public boolean isValid(String password, ConstraintValidatorContext ctx) {
            if (StringUtils.isNotEmpty(password)) {
//            return Pattern.matches("^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,16}$",password);
//            "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,16}$"

                //禁止默认消息返回
                ctx.disableDefaultConstraintViolation();
                //自定义返回消息
                ctx.buildConstraintViolationWithTemplate(Lang.get(message)).addConstraintViolation();
                return Pattern.matches(regex, password);
            } else {
                return true;
            }
        }

    }

}
