package com.minlia.module.pay.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.pay.enums.SysPayChannelEnum;
import com.minlia.module.pay.enums.SysPayMethodEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Data
@TableName("merchant_details")
@ApiModel(value = "MerchantDetailsEntity对象", description = "")
public class MerchantDetailsEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "列表id")
    @TableId("details_id")
    private String detailsId;

    @ApiModelProperty(value = "支付类型(支付渠道) 详情查看com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform对应子类，aliPay 支付宝， wxPay微信..等等")
    @TableField("pay_type")
    private SysPayChannelEnum payType;

    @ApiModelProperty(value = "支付方式")
    @TableField("pay_method")
    private SysPayMethodEnum payMethod;

    @ApiModelProperty(value = "应用id")
    @TableField("appid")
    private String appid;

    @ApiModelProperty(value = "商户id，商户号，合作伙伴id等等")
    @TableField("mch_id")
    private String mchId;

    @ApiModelProperty(value = "当前面私钥公钥为证书类型的时候，这里必填，可选值:PATH,STR,INPUT_STREAM")
    @TableField("cert_store_type")
    private String certStoreType;

    @ApiModelProperty(value = "私钥或私钥证书")
    @TableField("key_private")
    private String keyPrivate;

    @ApiModelProperty(value = "公钥或公钥证书")
    @TableField("key_public")
    private String keyPublic;

    @ApiModelProperty(value = "key证书,附加证书使用，如SSL证书，或者银联根级证书方面")
    @TableField("key_cert")
    private String keyCert;

    @ApiModelProperty(value = "私钥证书或key证书的密码")
    @TableField("key_cert_pwd")
    private String keyCertPwd;

    @ApiModelProperty(value = "异步回调")
    @TableField("notify_url")
    private String notifyUrl;

    @ApiModelProperty(value = "同步回调地址，大部分用于付款成功后页面转跳")
    @TableField("return_url")
    private String returnUrl;

    @ApiModelProperty(value = "签名方式,目前已实现多种签名方式详情查看com.egzosn.pay.common.util.sign.encrypt。MD5,RSA等等")
    @TableField("sign_type")
    private String signType;

    @ApiModelProperty(value = "收款账号，暂时只有支付宝部分使用，可根据开发者自行使用")
    @TableField("seller")
    private String seller;

    @ApiModelProperty(value = "子appid")
    @TableField("sub_app_id")
    private String subAppId;

    @ApiModelProperty(value = "子商户id")
    @TableField("sub_mch_id")
    private String subMchId;

    @ApiModelProperty(value = "编码类型，大部分为utf-8")
    @TableField("input_charset")
    private String inputCharset;

    @ApiModelProperty(value = "是否为测试环境: 0 否，1 测试环境")
    @TableField("is_test")
    private Boolean isTest;

    @ApiModelProperty(value = "是否禁用")
    @TableField("dis_flag")
    private Boolean disFlag;

}
