package com.minlia.modules.rbac.bean.to;

import com.minlia.cloud.body.Body;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by will on 9/8/17.
 */
@ApiModel("登录请求体")
@Data
public class LoginCredentialRequestBody implements Body {

  @NotBlank(message = "用户名不能为空")
  @ApiModelProperty(value = "用户名",example = "admin")
  private String username;

  @NotBlank(message = "密码不能为空")
  @ApiModelProperty(value = "密码",example = "admin")
  private String password;

}
