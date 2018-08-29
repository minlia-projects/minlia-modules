package com.minlia.module.common.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Annotation that check the cellphone
 * Created by garen on 2017/6/30.
 */
public class NameZhValidator implements ConstraintValidator<NameZh, String> {

    @Override
    public void initialize(NameZh cellphone) {

    }

    @Override
    public boolean isValid(String nameZh, ConstraintValidatorContext ctx) {
        if (StringUtils.isNotEmpty(nameZh)) {
            return Pattern.matches("^[\\u4e00-\\u9fa5]{2,6}$",nameZh);
        } else {
            return true;
        }
    }

}
