package com.minlia.aliyun.green.annotation;

import com.minlia.aliyun.green.bean.AliyunGreenImageResult;
import com.minlia.aliyun.green.constant.AliyunGreenCode;
import com.minlia.aliyun.green.enums.GreenLabelEnum;
import com.minlia.aliyun.green.service.AliyunGreenImageService;
import com.minlia.cloud.i18n.Lang;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author garen
 */
@Documented
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {GreenImage.GreenImageValidator.class})
public @interface GreenImage {

    String message() default "system.validator.message.antispam";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Deprecated
    GreenLabelEnum[] labels() default {GreenLabelEnum.ad,
            GreenLabelEnum.abuse,
            GreenLabelEnum.contraband,
            GreenLabelEnum.flood,
            GreenLabelEnum.politics,
            GreenLabelEnum.porn,
            GreenLabelEnum.spam,
            GreenLabelEnum.terrorism
    };

    @Component
    class GreenImageValidator implements ConstraintValidator<GreenImage, String> {

        private final AliyunGreenImageService aliyunGreenImageService;

        public GreenImageValidator(AliyunGreenImageService aliyunGreenImageService) {
            this.aliyunGreenImageService = aliyunGreenImageService;
        }

        private GreenLabelEnum[] labels;

        @Override
        public void initialize(GreenImage antispam) {
            labels = antispam.labels();
        }

        @Override
        public boolean isValid(String url, ConstraintValidatorContext ctx) {
            if (StringUtils.isNotEmpty(url)) {
                AliyunGreenImageResult result = aliyunGreenImageService.handle(url);
                if (result.isSuccess() && result.isBlock()) {
                    //禁止默认消息返回
                    ctx.disableDefaultConstraintViolation();
                    //自定义返回消息
                    ctx.buildConstraintViolationWithTemplate(Lang.get(AliyunGreenCode.Message.valueOf(result.getLabel()).i18nKey())).addConstraintViolation();
                    return false;
                }
            }
            return true;
        }

    }

}