package com.minlia.module.wechat.login.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.login.service.WechatLoginService;
import com.minlia.module.wechat.login.bean.WechatBindRO;
import com.minlia.module.wechat.login.bean.WechatLoginRO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2017/7/21.
 */
@Api(tags = "Wechat Login", description = "登录")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API + "auth")
public class WechatLoginEndpoint {

    @Autowired
    private WechatLoginService wechatLoginService;

    @ApiOperation(value = "根据公众号code登录", notes = "根据公众号code登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "login/wxmpcode", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response loginByWxMpCode(@Valid @RequestBody WechatLoginRO body) throws WxErrorException {
        return wechatLoginService.loginByWxMpCode(body);
    }

    @ApiOperation(value = "根据小程序code登录", notes = "根据小程序code登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "login/wxmacode", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response loginByWxMaCode(@Valid @RequestBody WechatLoginRO body) {
        return wechatLoginService.loginByWxMaCode(body);
    }

    @ApiOperation(value = "根据小程序绑定", notes = "根据小程序绑定", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "bind/wxma", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response bindByWxma(@RequestBody WechatBindRO body) {
        return wechatLoginService.bindByWxma(body);
    }

}