package com.minlia.module.wechat.ma.config;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Created by will on 9/12/17.
 */

@Data
@ApiModel(value = "小程序获取用户绑定的手机号码请求体")
public class PhoneNumberRequestBody {
  /**
   * 用户CODE
   */
  @ApiModelProperty(value = "用户CODE")
  private String code;
  /**
   * 解密一次性向量, 每次需要请求
   */
  @ApiModelProperty(value = "解密一次性向量, 每次需要请求")
  private String iv;
  /**
   * 微信返回的加密码后的数据
   */
  @ApiModelProperty(value = "微信返回的加密码后的数据")
  private String encryptedData;
}
