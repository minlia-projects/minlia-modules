package com.minlia.module.wechat.miniapp.endpoint;

import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.wechat.wechat.miniapp.phone.PhoneNumberRequestBody;
import com.minlia.module.wechat.wechat.miniapp.phone.PhoneNumberResponseBody;
import com.minlia.module.wechat.wechat.miniapp.phone.PhoneNumberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/30/17.
 */
@Slf4j
@RestController
@RequestMapping(value = ApiPrefix.API + "wechat/miniapp")
@Api(tags = "小程序公开接口", description = "小程序公开接口")
public class WechatMiniappOpenApiEndpoint {


  @Autowired
  private PhoneNumberService phoneNumberService;

  @ApiOperation(value = "获取当前登录用户绑定的手机号码", notes = "获取当前登录用户绑定的手机号码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "getPhoneNumber", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody getPhoneNumber(@RequestBody PhoneNumberRequestBody body) {
    PhoneNumberResponseBody responseBody = phoneNumberService.getBoundPhoneNumber(body);
    return SuccessResponseBody.builder().payload(responseBody).build();
  }

}
