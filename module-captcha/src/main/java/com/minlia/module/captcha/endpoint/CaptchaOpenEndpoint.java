package com.minlia.module.captcha.endpoint;

import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.constant.CaptchaCode;
import com.minlia.module.captcha.bean.domain.Captcha;
import com.minlia.module.captcha.service.CaptchaService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "System Captcha", description = "验证码")
@RestController
@RequestMapping(value = ApiPrefix.API + "captcha")
public class CaptchaOpenEndpoint {

    @Autowired
    CaptchaService captchaService;

    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send/{cellphone}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response send(@PathVariable String cellphone) throws ClientException {
        ApiAssert.state(Pattern.matches("^1[0-9]{10}$",cellphone), CaptchaCode.Message.CELLPHONE_WRONG_FORMAT);

        Captcha securityCode = captchaService.send(cellphone);

        //DEV环境时放出来
        if(Environments.isDevelopment()){
            return Response.success(securityCode);
        }else{
            return Response.success();
        }
    }

    @ApiOperation(value = "验证(测试用)", notes = "验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "verify/{cellphone}/{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void send(@PathVariable("cellphone") String cellphone, @PathVariable("code") String code) {
        ApiAssert.state(Pattern.matches("^1[0-9]{10}$",cellphone), CaptchaCode.Message.CELLPHONE_WRONG_FORMAT);
        captchaService.validity(cellphone,code);
    }

}
