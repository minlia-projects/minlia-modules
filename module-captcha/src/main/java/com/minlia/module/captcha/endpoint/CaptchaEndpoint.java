package com.minlia.module.captcha.endpoint;

import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.entity.Captcha;
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
public class CaptchaEndpoint {

    @Autowired
    CaptchaService securityCodeService;

    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send/{cellphone}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody send(@PathVariable String cellphone) throws ClientException {
        ApiPreconditions.not(Pattern.matches("^1[0-9]{10}$",cellphone),1,"请输入11位有效手机号码");

        Captcha securityCode = securityCodeService.send(cellphone);

        //DEV环境时放出来
        if(Environments.isDevelopment()){
            return SuccessResponseBody.builder().payload(securityCode).build();
        }else{
            return SuccessResponseBody.builder().build();
        }
    }

    @ApiOperation(value = "验证(测试用)", notes = "验证", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "verify/{cellphone}/{code}", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void send(@PathVariable("cellphone") String cellphone, @PathVariable("code") String code) {
        ApiPreconditions.not(Pattern.matches("^1[0-9]{10}$",cellphone),1,"请输入11位有效手机号码");
        securityCodeService.validity(cellphone,code);
    }

}
