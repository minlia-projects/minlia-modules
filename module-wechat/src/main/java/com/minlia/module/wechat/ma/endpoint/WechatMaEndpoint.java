package com.minlia.module.wechat.ma.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.ma.bean.ro.WechatMaUserDetailRO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaEncryptedDataRO;
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.module.wechat.ma.service.WechatMaUserService;
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
@Api(tags = "Wechat Mini App", description = "小程序")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "wechat/miniapp")
public class WechatMaEndpoint {

    private final WechatMaService wechatMaService;

    private final WechatMaUserService wechatMaUserService;

    public WechatMaEndpoint(WechatMaService wechatMaService, WechatMaUserService wechatMaUserService) {
        this.wechatMaService = wechatMaService;
        this.wechatMaUserService = wechatMaUserService;
    }

    @ApiOperation(value = "更新微信用户详情", notes = "更新微信用户详情", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "userinfo", method = RequestMethod.PUT, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response updateUserDetail(@Valid @RequestBody WechatMaUserDetailRO ro) {
        return Response.success(wechatMaUserService.update(ro));
    }

    @ApiOperation(value = "显示微信用户详情", notes = "显示微信用户详情", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "userinfo", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response showUserDetail() {
        return Response.success(wechatMaUserService.me());
    }

    @ApiOperation(value = "获取当前登录用户绑定的手机号码", notes = "获取当前登录用户绑定的手机号码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "getCellphone", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response getPhoneNumber(@RequestBody WechatMaEncryptedDataRO ro) {
        return Response.success(wechatMaService.getBoundPhoneNumber(ro));
    }

    @ApiOperation(value = "解密", notes = "解密", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "decrypt", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response decrypt(@RequestBody WechatMaUserDetailRO ro) {
        return Response.success(wechatMaService.decrypt(ro));
    }

}