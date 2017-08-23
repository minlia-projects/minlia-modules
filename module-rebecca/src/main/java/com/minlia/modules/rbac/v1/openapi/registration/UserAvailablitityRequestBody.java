package com.minlia.modules.rbac.v1.openapi.registration;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.annotation.i18n.Localize;
import com.minlia.cloud.annotation.i18n.Localized;
import com.minlia.cloud.constant.ValidationConstants;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

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
  @NotNull
  @Size(min = ValidationConstants.MIN_SIZE, max = ValidationConstants.MAX_SIZE, message = "Username have to be grater than 8 characters")
  @Column(unique = true)
  @JsonProperty
  private String username;
}
