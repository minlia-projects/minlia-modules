package com.minlia.module.unified.payment.alipay.v1;

import com.minlia.module.unified.payment.config.Certificate;
import com.minlia.module.unified.payment.config.Config;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by will on 9/14/17.
 */
public class AlipayConfig implements Config {

  private Certificate certificate;
  private String appId;

  /**
   * 交易通道在创建完之后服务端 Server to Server(S2S)通知回调的入口
   */
  private String callback;


  public AlipayConfig() {

    if (null != this.getCertificate()) {
      if (StringUtils.isEmpty(this.getCertificate().getPlatformPublicKey())) {
        throw new RuntimeException("支付宝支付需配置平台公钥");
      }
      if (StringUtils.isEmpty(this.getCertificate().getAppPublicKey())) {
        throw new RuntimeException("支付宝支付需配置应用公钥");
      }
      if (StringUtils.isEmpty(this.getCertificate().getAppPrivateKey())) {
        throw new RuntimeException("支付宝支付需配置应用私钥钥");
      }
      if (!StringUtils.isEmpty(this.getCertificate().getCertificatePath())) {
        throw new RuntimeException("支付宝支付无需配置证书路径");
      }
    }
  }


  @Override
  public Certificate getCertificate() {
    return certificate;
  }

  public void setCertificate(Certificate certificate) {
    this.certificate = certificate;
  }

  @Override
  public String getAppId() {
    return appId;
  }

  public void setAppId(String appId) {
    this.appId = appId;
  }

  @Override
  public String getCallback() {
    return callback;
  }

  public void setCallback(String callback) {
    this.callback = callback;
  }
}
