package com.minlia.module.wechat.ma.endpoint;

import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.ma.body.MiniappQrcodeRequestBody;
import com.minlia.module.wechat.ma.service.WechatMiniappService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by will on 6/30/17.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiPrefix.API + "wechat/miniapp")
@Api(tags = "Wechat Mini App", description = "小程序")
public class WechatMiniappOpenEndpoint {

    @Autowired
    private WechatMiniappService wechatMiniappService;

    @ApiOperation(value = "小程序二维码", notes = "小程序二维码", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "wxacode", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody createWxCodeLimit(@Valid @RequestBody MiniappQrcodeRequestBody body) throws Exception {
        return SuccessResponseBody.builder().payload(wechatMiniappService.createWxCodeLimit(body)).build();
    }

}
