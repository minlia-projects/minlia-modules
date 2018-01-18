package com.minlia.module.tencent.miniapp.endpoint;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.tencent.miniapp.service.WechatUserDetailService;
import com.minlia.module.tencent.miniapp.body.MiniappUserDetailRequestBody;
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
@RequestMapping(value = ApiPrefix.V1 + "wechat/miniapp")
@Api(tags = "小程序接口", description = "小程序接口")
public class WechatMiniappApiEndpoint {


  @Autowired
  private WechatUserDetailService wechatUserDetailService;

  /**
   * 已登录
   * 已绑定
   * <p>
   * 根据CODE取到OPENID, 再根据OPENID取到WechatOpenAccount
   * 当WechatOpenAccount存在时添加与WechatUserDetail关联, 并更新WechatUserDetail
   */
  @ApiOperation(value = "更新微信用户详情", notes = "更新微信用户详情", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "updateUserDetail", method = RequestMethod.PUT, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody updateUserDetail(@Valid @RequestBody MiniappUserDetailRequestBody body) {
    wechatUserDetailService.updateUserDetail(body);
    return SuccessResponseBody.builder().message("OK").build();
  }

  /**
   * 已登录
   * 已绑定
   * 已更新用户详情
   */
  @ApiOperation(value = "显示微信用户详情", notes = "显示微信用户详情", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "showUserDetail", method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody showUserDetail() {
    return SuccessResponseBody.builder().payload(wechatUserDetailService.showUserDetail()).build();
  }

}
