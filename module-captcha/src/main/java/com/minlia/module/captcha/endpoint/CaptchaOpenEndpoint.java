package com.minlia.module.captcha.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.captcha.bean.CaptchaSendResult;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.captcha.ro.CaptchaCRO;
import com.minlia.module.captcha.ro.CaptchaVerifyRO;
import com.minlia.module.captcha.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by garen on 2018/10/24.
 */
@Api(tags = "System Captcha", description = "验证码")
@RestController
@RequestMapping(value = ApiPrefix.API + "captcha")
public class CaptchaOpenEndpoint {

    @Autowired
    CaptchaService captchaService;

//    @ApiOperation(value = "发送", notes = "发送", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "send/{cellphone}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response send(@PathVariable String cellphone) {
//        ApiAssert.state(Pattern.matches("^1[0-9]{10}$",cellphone), CaptchaCode.Message.CELLPHONE_WRONG_FORMAT);
//
//        Captcha securityCode = captchaService.sendByCellphone(cellphone);
//
//        //DEV环境时放出来
//        if(Environments.isDevelopment()){
//            return Response.success(securityCode);
//        }else{
//            return Response.success();
//        }
//    }

    @AuditLog(value = "send otp")
    @ApiOperation(value = "发送", notes = "发送", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response send(@Valid @RequestBody CaptchaCRO cro) {
        Captcha captcha = captchaService.send(cro);

        //DEV环境时放出来
        if (Environments.isDevelopment()) {
//            return Response.success(captcha);
            return Response.success(CaptchaSendResult.builder().code(captcha.getCode()).countdown(captcha.getCountdown()).build());
        } else {
            return Response.success(CaptchaSendResult.builder().countdown(captcha.getCountdown()).build());
        }
    }

    @AuditLog(value = "verify otp")
    @ApiOperation(value = "验证", notes = "验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "verify", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response verify(@Valid @RequestBody CaptchaVerifyRO verifyRO) {
        if (verifyRO.getMethod().equals(CaptchaMethodEnum.CELLPHONE)) {
            ApiAssert.hasLength(verifyRO.getCellphone(), CaptchaCode.Message.CELLPHONE_NOT_NULL);
            captchaService.validityByCellphone(verifyRO.getCellphone(), verifyRO.getCode());
        } else {
            ApiAssert.hasLength(verifyRO.getCellphone(), CaptchaCode.Message.EMAIL_NOT_NULL);
            captchaService.validityByEmail(verifyRO.getEmail(), verifyRO.getCode());
        }
        return Response.success();
    }

}
