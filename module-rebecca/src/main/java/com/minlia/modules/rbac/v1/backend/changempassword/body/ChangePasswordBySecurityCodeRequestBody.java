package com.minlia.modules.rbac.v1.backend.changempassword.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.constant.ValidationConstants;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Created by will on 6/19/17.
 */
@Data
public class ChangePasswordBySecurityCodeRequestBody extends ChangePasswordRequestBody {

  /**
   * 验证码
   */
  @JsonProperty
  private String code;

  /**
   * 密码
   */
  @NotNull
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Password have to be grater than 8 characters")
  @JsonProperty
  private String newPassword;


}
