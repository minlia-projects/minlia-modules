package com.minlia.modules.rbac.v1.openapi.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.constant.ValidationConstants;
import com.minlia.modules.rbac.validation.UniqueUsername;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

/**
 * Created by will on 6/19/17.
 * 用户注册请求体
 */
@Data
public class UserRegistrationRequestBody {

  /**
   * 用户名
   */
  @NotNull
  @UniqueUsername //放这里就需要考虑修改时候的BODY分离
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Username have to be grater than 8 characters")
  @Column(unique = true)
  @JsonProperty
  private String username;

  /**
   * 密码
   */
  @NotNull
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Password have to be grater than 8 characters")
  @JsonProperty
  private String password;


  @JsonProperty
  private String code;

}
