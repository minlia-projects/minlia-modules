package com.minlia.modules.rbac.openapi.registration.body;

import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.constant.ValidationConstants;
import com.minlia.module.common.validation.Cellphone;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by will on 6/22/17.
 * 用户有效性验证请求体
 */
@Data
@Localized
public class UserAvailablitityRequestBody {

  /**
   * 用户名
   */
  @NotBlank
  @Cellphone
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Username have to be grater than 8 characters")
  private String username;

}
