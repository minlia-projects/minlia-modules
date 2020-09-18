//package com.minlia.module.common.validation;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Value;
//
//import javax.validation.ConstraintValidator;
//import javax.validation.ConstraintValidatorContext;
//import java.util.regex.Pattern;
//
///**
// * Annotation that check the password
// *
// * @author garen
// * @date 2017/6/30
// */
//public class PasswordValidator implements ConstraintValidator<Password, String> {
//
//    @Value("${system.valid.rule.password}")
//    private String regex;
//
//
//    @Override
//    public void initialize(Password password) {
//
//    }
//
//    @Override
//    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
//        if (StringUtils.isNotEmpty(password)) {
////            return Pattern.matches("^(?![A-Z]+$)(?![a-z]+$)(?!\\d+$)(?![\\W_]+$)\\S{6,16}$",password);
////            "^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W]){1,})(?!.*\\s).{8,16}$"
//            return Pattern.matches(regex, password);
//        } else {
//            return true;
//        }
//    }
//
//}