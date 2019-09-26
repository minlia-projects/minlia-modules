package com.minlia.module.common.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Annotation that check the cellphone
 * Created by garen on 2017/6/30.
 */
public class UsernameValidator implements ConstraintValidator<Username, String> {

    @Override
    public void initialize(Username username) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotEmpty(username)) {
            return Pattern.matches("^[a-zA-z][a-zA-Z0-9_]{2,50}$",username);
        } else {
            return true;
        }
    }

}