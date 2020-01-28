package com.minlia.module.common.validation;

import cn.hutool.setting.dialect.Props;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Annotation that check the password
 * Created by garen on 2017/6/30.
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

//    @Value("${system.valid.rule.password}")
    public String pattern;

    @Override
    public void initialize(Password password) {
        if (null == pattern) {
            Props props = new Props("config/application.properties");
            String rule = props.getProperty("system.valid.rule.password");
            this.pattern = rule;
        }
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotEmpty(password)) {
            return Pattern.matches(pattern, password);
//            return Pattern.matches("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,16}$",password);
        } else {
            return true;
        }
    }

}
