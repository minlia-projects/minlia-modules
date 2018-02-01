package com.minlia.modules.rbac.validation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * Annotation that check the id card
 * Created by garen on 2017/6/30.
 */
public class IdCardValidator implements ConstraintValidator<IdCard, String> {

    @Override
    public void initialize(IdCard idCard) {

    }

    @Override
    public boolean isValid(String idCard, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotEmpty(idCard)) {
            return Pattern.matches("(^[1-9]\\d{5}(18|19|([23]\\d))\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{2}[0-9Xx]$)",idCard);
        } else {
            return true;
        }
    }

}
