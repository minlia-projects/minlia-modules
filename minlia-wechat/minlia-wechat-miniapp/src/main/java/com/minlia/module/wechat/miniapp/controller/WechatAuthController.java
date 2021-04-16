package com.minlia.module.wechat.miniapp.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.miniapp.bean.WechatMaLoginRo;
import com.minlia.module.wechat.miniapp.service.WechatAuthService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author garen
 */
@Api(tags = "Wechat Miniapp Auth", description = "微信-小程序-认证")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API + "auth")
@RequiredArgsConstructor
public class WechatAuthController {

    private final WechatAuthService wechatAuthService;

    @ApiOperation(value = "登录")
    @PostMapping(value = "login/miniapp")
    public Response loginByWxMaCode(@Valid @RequestBody WechatMaLoginRo body) {
        return wechatAuthService.login(body);
    }

//    @ApiOperation(value = "绑定")
//    @RequestMapping(value = "bind")
//    public Response bindByWxma(@RequestBody WechatBindRO body) {
//        return wechatLoginService.bindByWxma(body);
//    }

}