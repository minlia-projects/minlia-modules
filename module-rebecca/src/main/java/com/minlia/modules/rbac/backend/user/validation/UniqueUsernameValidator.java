package com.minlia.modules.rbac.backend.user.validation;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.rbac.openapi.registration.body.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.openapi.registration.service.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Validator of unique username
 */
@Slf4j
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    UserRegistrationService userRegistrationService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // If the service is null then return null
        if(userRegistrationService == null){
            log.warn("Object is null at this time: userReadOnlyService");
            return true;
        }
        // Check if the username is unique
        UserAvailablitityRequestBody body=new UserAvailablitityRequestBody();
        body.setUsername(username);
        return userRegistrationService.availablitity(body).getStatus().equals(StatefulBody.SUCCESS);
    }
}
