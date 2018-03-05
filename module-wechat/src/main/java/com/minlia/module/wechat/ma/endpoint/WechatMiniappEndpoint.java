package com.minlia.module.wechat.ma.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.service.WechatUserInfoService;
import com.minlia.module.wechat.wechat.miniapp.phone.PhoneNumberRequestBody;
import com.minlia.module.wechat.wechat.miniapp.phone.PhoneNumberResponseBody;
import com.minlia.module.wechat.wechat.miniapp.phone.PhoneNumberService;
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
 * 小程序接口
 */
@Api(tags = "Wechat Mini App", description = "小程序接口")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wechat/miniapp")
public class WechatMiniappEndpoint {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Autowired
    private WechatUserInfoService wechatUserDetailService;

    @ApiOperation(value = "更新微信用户详情", notes = "更新微信用户详情", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "updateUserDetail", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody updateUserDetail(@Valid @RequestBody MiniappUserDetailRequestBody body) {
        wechatUserDetailService.updateUserDetail(body);
        return SuccessResponseBody.builder().message("OK").build();
    }

    @ApiOperation(value = "显示微信用户详情", notes = "显示微信用户详情", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "showUserDetail", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody showUserDetail() {
        return SuccessResponseBody.builder().payload(wechatUserDetailService.showUserDetail()).build();
    }

    @ApiOperation(value = "获取当前登录用户绑定的手机号码", notes = "获取当前登录用户绑定的手机号码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "getPhoneNumber", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody getPhoneNumber(@RequestBody PhoneNumberRequestBody body) {
        PhoneNumberResponseBody responseBody = phoneNumberService.getBoundPhoneNumber(body);
        return SuccessResponseBody.builder().payload(responseBody).build();
    }

}