package com.minlia.modules.rbac.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.captcha.entity.Captcha;
import com.minlia.module.captcha.ro.CaptchaCRO;
import com.minlia.module.captcha.enumeration.CaptchaMethodEnum;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.constant.UserCode;
import com.minlia.modules.rbac.context.SecurityContextHolder;
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
 * Created by garen on 2017/6/29.
 */
@Api(tags = "System Captcha", description = "验证码")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "captcha")
public class UserCaptchaEndpoint {

    @Autowired
    private CaptchaService captchaService;

    @AuditLog(value = "send otp to curent user")
    @ApiOperation(value = "发送当前用户验证码", notes = "发送当前用户验证码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response send(@Valid @RequestBody CaptchaCRO to) {
        User user = SecurityContextHolder.getCurrentUser();
        if (to.getMethod().equals(CaptchaMethodEnum.CELLPHONE)) {
            ApiAssert.hasLength(user.getCellphone(), UserCode.Message.NOT_SET_CELLPHONE);
            to.setCellphone(user.getCellphone());
        } else {
            ApiAssert.hasLength(user.getEmail(), UserCode.Message.NOT_SET_EMAIL);
            to.setEmail(user.getEmail());
        }

        Captcha captcha = captchaService.send(to);
        //DEV环境时放出来
        if(Environments.isDevelopment()){
            return Response.success(captcha);
        }else{
            return Response.success();
        }
    }

}
