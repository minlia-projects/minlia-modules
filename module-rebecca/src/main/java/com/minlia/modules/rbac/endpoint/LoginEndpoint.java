package com.minlia.modules.rbac.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.rbac.bean.to.LoginCredentialRequestBody;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 7/21/17.
 * This is just a fake control for springfox-swagger2 to generate api-docs
 */
@Api(tags = "System Login", description = "登录")
@CrossOrigin
@RestController
@RequestMapping(value = ApiPrefix.API+"auth/login")
public class LoginEndpoint {

//  @Autowired
//  private LoginThirdPartyService loginThirdPartyService;

  @ApiOperation(value = "登录", notes = "登录")
  @RequestMapping(value = "", method = RequestMethod.POST, produces = "application/json")
  public StatefulBody login(@Valid @RequestBody LoginCredentialRequestBody credential) {
    return SuccessResponseBody.builder().build();
  }

//  @ApiOperation(value = "根据公众号code登录", notes = "根据公众号code登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "wxmpcode", produces = {MediaType.APPLICATION_JSON_VALUE})
//  public StatefulBody loginByWxMpCode(@Valid @RequestBody LoginWechatRequestBody body) throws WxErrorException {
//    return loginThirdPartyService.loginByWxMpCode(body);
//  }
//
//  @ApiOperation(value = "根据小程序code登录", notes = "根据小程序code登录", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
//  @PostMapping(value = "wxmacode", produces = {MediaType.APPLICATION_JSON_VALUE})
//  public StatefulBody loginByWxMaCode(@Valid @RequestBody LoginWechatRequestBody body) {
//    return loginThirdPartyService.loginByWxMaCode(body);
//  }

}
