package com.minlia.module.wechat.miniapp.endpoint;

import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.miniapp.service.WechatMiniappService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 小程序接口
 */
@Slf4j
@RestController
@RequestMapping(value = ApiPrefix.API + "wechat/miniapp")
@Api(tags = "小程序接口", description = "小程序接口")
public class WechatMiniappEndpoint {

    @Autowired
    private WechatMiniappService wechatMiniappService;

//    @ApiOperation(value = "通过code获取OpenId检测是否已与系统用户绑定或登录", notes = "通过code获取OpenId检测是否已与系统用户绑定", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "account/isBind/{code}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody isBind(@PathVariable String code, HttpServletRequest request, HttpServletResponse response) {
//        return SuccessResponseBody.builder().payload(wechatMiniappService.isBound(code)).build();
//    }
//
//    @ApiOperation(value = "绑定OpenId或注册用户", notes = "绑定OpenId或注册用户", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "bindOrRegistration", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ApiResponseBody bindOrRegistration(@RequestBody BindOrRegistrationRequestBody body) throws WxErrorException {
//        wechatMiniappService.bindOrRegistration(body);
//        return SuccessResponseBody.builder().message("OK").build();
//    }

//    @ApiOperation(value = "小程序二维码", notes = "小程序二维码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//    @RequestMapping(value = "wxacode", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public StatefulBody createWxCodeLimit(@Valid @RequestBody MiniappQrcodeRequestBody body) throws Exception {
//        return SuccessResponseBody.builder().payload(wechatMiniappService.createWxCodeLimit(body)).build();
//    }

}