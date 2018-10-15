package com.minlia.modules.rbac.backend;

import com.aliyuncs.exceptions.ClientException;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.bean.domain.Captcha;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/19/17.
 */
@Api(tags = "System Captcha", description = "验证码")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "captcha")
public class CaptchaEndpoint {

    @Autowired
    private CaptchaService captchaService;

    @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "send", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response send() throws ClientException {
        String cellphone = SecurityContextHolder.getCurrentUser().getCellphone();
        if (StringUtils.isEmpty(cellphone)) {
            return Response.failure(RebaccaCode.Message.USER_NO_CELLPHONE);
        } else {
            Captcha securityCode = captchaService.send(cellphone);

            //DEV环境时放出来
            if(Environments.isDevelopment()){
                return Response.success(securityCode);
            }else{
                return Response.success();
            }
        }
    }

}
