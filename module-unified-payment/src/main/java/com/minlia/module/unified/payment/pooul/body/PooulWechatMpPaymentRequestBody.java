package com.minlia.module.unified.payment.pooul.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 微信公众号支付-原生
 * Created by will on 9/10/17.
 */
@XmlRootElement(name = "xml")
@Data
//@Signature
public class PooulWechatMpPaymentRequestBody extends PooulApiHttpRequestBody {

    //  /**
//   * 是否小程序支付	is_minipg	否	String(1)	值为1，表示小程序支付；不传或值不为1，表示公众账号内支付
//   */
//  @XmlElement(name = "is_minipg")
//  @JsonProperty(value = "is_minipg")
//  private String  isMinipg="1";

    /**
     * 商户订单号  String(32)  商户系统内部的订单号, 最长为32个字符, 可包含字母, 需要确保在商户系统唯一
     * 必填
     */
    @XmlElement(name = "mch_trade_id", required = true)
    @JsonProperty(value = "mch_trade_id")
    private String mchTradeId;

    /**
     * 商品描述	  String(127）
     * 必填
     */
    @XmlElement(name = "bean", required = true)
    @JsonProperty(value = "bean")
    private String body;

    /**
     * 用户openid	sub_openid  String(128)	微信用户关注商家公众号的openid，配置北京农商银行渠道时为空
     * 必填
     */
    @XmlElement(name = "sub_openid", required = true)
    @JsonProperty(value = "sub_openid")
    private String subOpenid;

    /**
     * 公众账号或小程序ID
     * 当发起公众号支付时，值是微信公众平台基本配置中的AppID(应用ID)；当发起小程序支付时，值是对应小程序的AppID；配置北京农商银行渠道时为空
     * 必填
     * String(32)
     */
    @XmlElement(name = "sub_appid", required = true)
    @JsonProperty(value = "sub_appid")
    private String subAppid;

    /**
     * 总金额  以分为单位，不允许包含任何字、 符号
     * 必填
     * int
     */
    @XmlElement(name = "total_fee", required = true)
    @JsonProperty(value = "total_fee")
    private Integer totalFee;

    /**
     * 终端IP  订单生成的机器 IP
     * 必填
     * String(16)
     */
    @XmlElement(name = "spbill_create_ip", required = true)
    @JsonProperty(value = "spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 通知地址 接收平台通知的URL，需给绝对路径，255字符内
     * 必填
     * String(255)
     */
    @XmlElement(name = "notify_url", required = true)
    @JsonProperty(value = "notify_url")
    private String notifyUrl;

    /**
     * 前台地址 交易完成后跳转的URL，需给绝对路径，255字符内，注:该地址只作为前端页面的一个跳转，需使用notify_url通知结果作为支付最终结果。此参数只在跳转方式下才有效。
     * 否
     * String(255)
     */
    @XmlElement(name = "callback_url", required = true)
    @JsonProperty(value = "callback_url")
    private String callbacUrl;

    /**
     * 订单发起时间 时间戳：自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。
     * 否
     * int
     */
    @XmlElement(name = "time_start")
    @JsonProperty(value = "time_start")
    private Integer timeStart;

    /**
     * 订单失效时间 时间戳：自1970年1月1日 0点0分0秒以来的秒数。注意：部分系统取到的值为毫秒级，需要转换成秒(10位数字)。
     * 否
     * int
     */
    @XmlElement(name = "time_expire")
    @JsonProperty(value = "time_expire")
    private Long timeExpire;

    /**
     * 随机字符串  随机字符串，不长于32位
     * 必填
     * String(32)
     */
    @XmlElement(name = "nonce_str", required = true)
    @JsonProperty(value = "nonce_str")
    private String nonceStr = RandomStringUtils.randomAlphabetic(32);

    /**
     * 是否限制信用卡  限定用户使用微信支付时能否使用信用卡，值为1，禁用信用卡；值为0或者不传此参数则不禁用
     * 否
     * String
     */
    @XmlElement(name = "limit_pay")
    @JsonProperty(value = "limit_pay")
    private Integer limitPay;

    /**
     * 是否采用D0通道	Int	非必须字段。采用D0接口时需添加此字段，默认为1,1：否，0：是
     * 否
     * int
     */
    @XmlElement(name = "settle_type")
    @JsonProperty(value = "settle_type")
    private Integer settleType;

    /**
     * 签名 MD5签名结果，详见签名算法
     * 必填
     */
    @XmlElement(name = "sign", required = true)
    @JsonProperty(value = "sign")
    private String sign;


}
