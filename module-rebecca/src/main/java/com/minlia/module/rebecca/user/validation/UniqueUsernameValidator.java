package com.minlia.module.rebecca.user.validation;

import com.minlia.module.rebecca.user.bean.UserAvailablitityTo;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator of unique username
 *
 * @author garen
 */
@Slf4j
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    SysUserRegisterService userRegistrationService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // If the service is null then return null
        if (userRegistrationService == null) {
            log.warn("Object is null at this time: userReadOnlyService");
            return true;
        }
        // Check if the username is unique
        UserAvailablitityTo to = new UserAvailablitityTo();
        to.setUsername(username);
        return userRegistrationService.availablitity(to).isSuccess();
    }
}
