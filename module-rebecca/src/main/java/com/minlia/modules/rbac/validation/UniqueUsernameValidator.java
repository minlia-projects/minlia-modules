package com.minlia.modules.rbac.validation;

import com.minlia.modules.rbac.repository.UserRepository;
import com.minlia.modules.rbac.service.UserReadOnlyService;
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
    UserReadOnlyService userReadOnlyService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        // If the repository is null then return null
        if(userReadOnlyService == null){
            log.warn("Object is null at this time: userReadOnlyService");
            return true;
        }
        // Check if the username is unique
        return userReadOnlyService.findOneByUsernameOrEmailOrCellphone(username) == null;
    }
}
