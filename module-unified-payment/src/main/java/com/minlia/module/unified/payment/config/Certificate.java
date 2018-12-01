package com.minlia.module.unified.payment.config;

import lombok.Data;

/**
 * Created by will on 9/14/17.
 */
@Data
public class Certificate {

  /**
   * 应用公钥
   */
  private String appPublicKey;

  /**
   * 应用私钥
   */
  private String appPrivateKey;

  /**
   * 平台公钥
   */
  private String platformPublicKey;

  /**
   * 证书路径：微信需要使用
   */
  private String certificatePath;

}
