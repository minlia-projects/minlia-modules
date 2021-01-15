package com.minlia.module.captcha.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.captcha.bean.CaptchaCro;
import com.minlia.module.captcha.bean.CaptchaSendResult;
import com.minlia.module.captcha.bean.CaptchaVerifyRo;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.CaptchaEntity;
import com.minlia.module.captcha.enumeration.CaptchaTypeEnum;
import com.minlia.module.captcha.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 验证码 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-25
 */
@Api(tags = "System Captcha", description = "验证码")
@RestController
@RequestMapping(value = ApiPrefix.API + "captcha")
public class CaptchaController {

    final CaptchaService captchaService;

    public CaptchaController(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @AuditLog(value = "send captcha", type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "发送")
    @PostMapping(value = "send")
    public Response send(@Valid @RequestBody CaptchaCro cro) {
        CaptchaEntity captcha = captchaService.send(cro);

        //DEV环境时放出来
        if (Environments.isDevelopment()) {
            return Response.success(CaptchaSendResult.builder().vcode(captcha.getVcode()).countdown(captcha.getCountdown()).build());
        } else {
            return Response.success(CaptchaSendResult.builder().countdown(captcha.getCountdown()).build());
        }
    }

    @AuditLog(value = "verify captcha", type = AuditOperationTypeEnum.UPDATE)
    @ApiOperation(value = "验证")
    @PostMapping(value = "verify")
    public Response verify(@Valid @RequestBody CaptchaVerifyRo verifyRo) {
        if (verifyRo.getType().equals(CaptchaTypeEnum.CELLPHONE)) {
            ApiAssert.hasLength(verifyRo.getCellphone(), CaptchaCode.Message.CELLPHONE_NOT_NULL);
            return captchaService.validity(verifyRo.getAreaCode() + verifyRo.getCellphone(), verifyRo.getVcode());
        } else {
            ApiAssert.hasLength(verifyRo.getEmail(), CaptchaCode.Message.EMAIL_NOT_NULL);
            return captchaService.validity(verifyRo.getEmail(), verifyRo.getVcode());
        }
    }

}