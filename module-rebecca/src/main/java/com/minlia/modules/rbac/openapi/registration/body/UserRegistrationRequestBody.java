package com.minlia.modules.rbac.openapi.registration.body;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.cloud.constant.ValidationConstants;
import com.minlia.module.common.validation.Cellphone;
import com.minlia.module.common.validation.Password;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by will on 6/19/17.
 * 用户注册请求体
 */
@Data
public class UserRegistrationRequestBody implements ApiRequestBody {

  /**
   * 用户名
   */
  @NotBlank
  @Cellphone
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Username have to be grater than 8 characters")
  private String username;

  /**
   * 密码
   */
  @NotBlank
  @Password
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Password have to be grater than 8 characters")
  private String password;

  @NotBlank
  @Size(min = 4,max = 6)
  private String code;

  @ApiModelProperty("推荐人")
  @Size(max = 50)
  private String referral;

}