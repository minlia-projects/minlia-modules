package com.minlia.module.unified.payment.config;

/**
 * Created by will on 9/14/17.
 */
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


  public String getAppPublicKey() {
    return appPublicKey;
  }

  public void setAppPublicKey(String appPublicKey) {
    this.appPublicKey = appPublicKey;
  }

  public String getAppPrivateKey() {
    return appPrivateKey;
  }

  public void setAppPrivateKey(String appPrivateKey) {
    this.appPrivateKey = appPrivateKey;
  }

  public String getPlatformPublicKey() {
    return platformPublicKey;
  }

  public void setPlatformPublicKey(String platformPublicKey) {
    this.platformPublicKey = platformPublicKey;
  }

  public String getCertificatePath() {
    return certificatePath;
  }

  public void setCertificatePath(String certificatePath) {
    this.certificatePath = certificatePath;
  }

}
