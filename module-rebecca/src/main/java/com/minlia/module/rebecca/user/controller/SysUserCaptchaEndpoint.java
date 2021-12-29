package com.minlia.module.rebecca.user.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.captcha.bean.CaptchaCro;
import com.minlia.module.captcha.entity.CaptchaEntity;
import com.minlia.module.captcha.enums.CaptchaTypeEnum;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author garen
 * @date 2017/6/29
 */
@Api(tags = "System User Captcha", description = "用户-验证码")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "captcha")
public class SysUserCaptchaEndpoint {

    private final CaptchaService captchaService;

    public SysUserCaptchaEndpoint(CaptchaService captchaService) {
        this.captchaService = captchaService;
    }

    @AuditLog(value = "send otp to current user", type = AuditOperationTypeEnum.CREATE)
    @ApiOperation(value = "发送当前用户验证码", notes = "发送当前用户验证码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response send(@Valid @RequestBody CaptchaCro to) {
        SysUserEntity user = SecurityContextHolder.getCurrentUser();
        if (to.getType().equals(CaptchaTypeEnum.CELLPHONE)) {
            ApiAssert.hasLength(user.getCellphone(), SysUserCode.Message.NOT_SET_CELLPHONE);
            to.setCellphone(user.getCellphone());
        } else {
            ApiAssert.hasLength(user.getEmail(), SysUserCode.Message.NOT_SET_EMAIL);
            to.setEmail(user.getEmail());
        }

        CaptchaEntity captcha = captchaService.send(to);
        //DEV环境时放出来
        if (Environments.isDevelopment()) {
            return Response.success(captcha);
        } else {
            return Response.success();
        }
    }

}
