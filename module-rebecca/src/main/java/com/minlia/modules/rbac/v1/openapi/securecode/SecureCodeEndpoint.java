package com.minlia.modules.rbac.v1.openapi.securecode;

/**
 * Created by will on 8/24/17.
 */


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.cloud.utils.Environments;
import com.minlia.module.captcha.v1.body.SecureCodeSendRequestBody;
import com.minlia.module.captcha.v1.domain.SecureCode;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;
import com.minlia.module.captcha.v1.service.SecureCodeService;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.repository.UserRepository;
import com.minlia.modules.rbac.service.UserReadOnlyService;
import com.minlia.modules.rbac.service.UserWriteOnlyService;
import com.minlia.modules.security.code.SecurityApiCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by will on 6/19/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.API + "user/secureCode")
@Api(tags = "Open Api", description = "开放接口")
@Slf4j
public class SecureCodeEndpoint {

  @Autowired
  Environment environment;

  @Autowired
  UserWriteOnlyService userWriteOnlyService;

  @Autowired
  UserReadOnlyService userReadOnlyService;

  @Autowired
  UserRepository userRepository;

  @Autowired
  SecureCodeService secureCodeService;

  /**
   * 根据手机号码发送验证码
   * <br />
   *
   * API 样式
   *
   * api/user/securityCode/USER_REGISTRATION/send
   * api/user/securityCode/RESET_PASSWORD/send
   */
  @ApiOperation(value = "发送验证码", notes = "发送验证码", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @RequestMapping(value = "{scene}/send", method = RequestMethod.POST, consumes = {
      MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
  public StatefulBody send(@Valid @RequestBody SecureCodeSendRequestBody body,
      @PathVariable String scene) {

    //获取验证码类型
    SecureCodeSceneEnum sceneSpecified = SecureCodeSceneEnum.valueOf(scene);

    //当为重置密码时,需要校验用户是否存在
    if (sceneSpecified.equals(SecureCodeSceneEnum.RESET_PASSWORD)) {
      User user = userReadOnlyService.findOneByUsernameOrEmailOrCellphone(body.getUsername());
      ApiPreconditions.checkNotNull(user, SecurityApiCode.INVALID_USER);
    }

    SecureCode securityCode = secureCodeService.send(body, sceneSpecified);
    //非生产环境时放出来
    if (!Environments.isProduction()) {
      return SuccessResponseBody.builder().payload(securityCode).build();
    } else {
      return SuccessResponseBody.builder().build();
    }

  }


}

