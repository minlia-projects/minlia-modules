package com.minlia.module.unified.payment.pooul.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import lombok.Data;

import javax.xml.bind.annotation.XmlElement;

/**
 * Created by will on 9/10/17.
 * swiftpass接口公共请求参数
 */
@Data
public class PooulApiHttpRequestBody implements Body {

  /**
   * 交易类型: wechat.jswap
   * 必填
   */
  @XmlElement(name = "pay_type")
  @JsonProperty(value = "pay_type")
  private String payType;

  /**
   * 商户号  由平台分配
   * 必填
   */
  @XmlElement(name = "merchant_id")
  @JsonProperty(value = "merchant_id")
  private String merchantId;

  /**
   * 版本号	version	否	String(8)	版本号，version默认值是2.0
   */
//  @XmlElement(name = "version")
//  @JsonProperty(value = "version")
//  private String version="1.0";

  /**
   * 签名方式	sign_type	否	String(8)	签名类型，取值：MD5默认：MD5
   */
  @XmlElement(name = "sign_type")
  @JsonProperty(value = "sign_type")
  private String signType="MD5";

}
