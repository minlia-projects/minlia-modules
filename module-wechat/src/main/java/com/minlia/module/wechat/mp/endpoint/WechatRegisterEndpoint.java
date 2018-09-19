package com.minlia.module.wechat.mp.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.mp.body.BindWxRequestBody;
import com.minlia.module.wechat.mp.service.LoginThirdPartyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2017/7/21.
 */
@Api(tags = "Wechat Login", description = "登录")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API + "auth")
public class WechatRegisterEndpoint {

    @Autowired
    private LoginThirdPartyService loginThirdPartyService;

    @ApiOperation(value = "根据小程序绑定", notes = "根据小程序绑定", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "bind/wxma", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response bindByWxma(@RequestBody BindWxRequestBody body) throws WxErrorException {
        return loginThirdPartyService.bindByWxma(body);
    }

}
