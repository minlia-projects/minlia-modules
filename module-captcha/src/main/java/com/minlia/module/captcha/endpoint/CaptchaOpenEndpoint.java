package com.minlia.module.captcha.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.bean.domain.Captcha;
import com.minlia.module.captcha.bean.to.CaptchaTO;
import com.minlia.module.captcha.bean.to.CaptchaVerifyTO;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
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

    @ApiOperation(value = "发送", notes = "发送", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response send(@Valid @RequestBody CaptchaTO to) {
        Captcha captcha = captchaService.send(to);
        //DEV环境时放出来
        if(Environments.isDevelopment()){
            return Response.success(captcha);
        }else{
            return Response.success();
        }
    }

    @ApiOperation(value = "验证", notes = "验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "verify", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response verify(@Valid @RequestBody CaptchaVerifyTO to) {
        if (to.getMethod().equals(CaptchaMethodEnum.CELLPHONE)) {
            ApiAssert.hasLength(to.getCellphone(), CaptchaCode.Message.CELLPHONE_NOT_NULL);
            captchaService.validityByCellphone(to.getCellphone(), to.getCode());
        } else {
            ApiAssert.hasLength(to.getCellphone(), CaptchaCode.Message.EMAIL_NOT_NULL);
            captchaService.validityByEmail(to.getEmail(), to.getCode());
        }
        return Response.success();
    }

}
