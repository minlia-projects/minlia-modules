package com.minlia.modules.rbac.v1.openapi.authentication;

import com.minlia.cloud.body.Body;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created by will on 9/8/17.
 */
@ApiModel("登录请求体")
@Data
public class LoginCredentialRequestBody implements Body {


  @NotNull
  @ApiModelProperty(value = "用户名",example = "admin")
  private String username;


  @NotNull
  @ApiModelProperty(value = "密码",example = "admin")
  private String password;


}
