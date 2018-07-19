package com.minlia.module.pooul.body.jsminipg;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 //for swiftpass only
 <xml><bank_type><![CDATA[CFT]]></bank_type>
 <charset><![CDATA[UTF-8]]></charset>
 <fee_type><![CDATA[CNY]]></fee_type>
 <is_subscribe><![CDATA[N]]></is_subscribe>
 <mch_id><![CDATA[101500146325]]></mch_id>
 <nonce_str><![CDATA[1508747368049]]></nonce_str>
 <openid><![CDATA[o-Rj7wDPLuZy4lcJBCFz-wkoMR8Y]]></openid>
 <out_trade_no><![CDATA[O15087473265905034]]></out_trade_no>
 <out_transaction_id><![CDATA[4200000016201710239841981115]]></out_transaction_id>
 <pay_result><![CDATA[0]]></pay_result>
 <result_code><![CDATA[0]]></result_code>
 <sign><![CDATA[D3BA5BD6BB6CD85DC2CBC821742C64DE]]></sign>
 <sign_type><![CDATA[MD5]]></sign_type>
 <status><![CDATA[0]]></status>
 <sub_appid><![CDATA[wx8d0da24b30e0f110]]></sub_appid>
 <sub_is_subscribe><![CDATA[N]]></sub_is_subscribe>
 <sub_openid><![CDATA[oICYK0S0kMA1VKT33LpD-4VDhK3g]]></sub_openid>
 <time_end><![CDATA[20171023162927]]></time_end>
 <total_fee><![CDATA[1]]></total_fee>
 <trade_type><![CDATA[pay.weixin.jspay]]></trade_type>
 <transaction_id><![CDATA[101500146325201710232216127980]]></transaction_id>
 <version><![CDATA[2.0]]></version>
 </xml>

 attach=TS100000000001149245184212600276&bank_type=CFT&charset=UTF-8&fee_type=CNY&is_subscribe=N&mch_id=103580003270&nonce_str=1492451851250&openid=o7PjgtyHptTwPILLfptTmplDomFg&out_trade_no=TS100000000001149245184212600276&out_transaction_id=4004642001201704187397984756&pay_result=0&result_code=0&status=0&sub_appid=wx62eb98733173fa88&sub_is_subscribe=Y&sub_openid=oGM7bwGdkotqW25z8Bwm05hrXDZY&time_end=20170418015730&total_fee=1&trade_type=pay.weixin.jspay&transaction_id=103580003270201704181196670406&version=2.0&key=d83e2ec91d72e4a00ffa7177353f8c47
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
//@PaymentSignedEntity
public class PooulCallbackRequestBody implements Serializable /*implements SignableNotificationRequestBody*/ {

    //后台通知通过请求中的notify_url进行， post

    /**
     * 版本号      version默认值是2.0。
     */
    @XmlElement(name = "version")
    private String version;

    /**
     * 字符集	可选值 UTF-8 ，默认为 UTF-8。
     */
    @XmlElement(name = "charset")
    private String charset;

    /**
     * 签名方式	签名类型，取值：MD5默认：MD5
     */
    @XmlElement(name = "sign_type")
    private String signType;

    /**
     * 返回状态码	 0表示成功，非0表示失败此字段是通信标识，非交易标识，交易是否成功需要查看 result_code 来判断
     */
    @XmlElement(name = "status")
    private String status;

    /**
     * 返回信息，如非空，为错误原因签名失败参数格式校验错误
     */
    @XmlElement(name = "message")
    private String message;



    //以下字段在 status 为 0的时候有返回

    /**
     * 业务结果	0表示成功非0表示失败
     */
    @XmlElement(name = "result_code")
    private String resultCode;

    /**
     * 商户号，由平台分配
     */
    @XmlElement(name = "mch_id")
    private String mchId;

    /**
     * 终端设备号
     */
    @XmlElement(name = "device_info")
    private String deviceInfo;

    /**
     * 随机字符串，不长于 32 位
     */
    @XmlElement(name = "nonce_str")
    private String nonceStr;

    /**
     * 错误代码 参考错误码
     */
    @XmlElement(name = "err_code")
    private String errCode;

    /**
     * 错误代码描述   结果信息描述
     */
    @XmlElement(name = "err_msg")
    private String errMsg;

    /**
     * 签名   MD5签名结果，详见“安全规范”
     */
    @XmlElement(name = "sign")
    private String sign;

    //以下字段在 status 和 result_code 都为 0的时候有返回

    /**
     * 用户在商户 appid 下的唯一标识
     */
    @XmlElement(name = "openid")
    private String openid;

    /**
     * 交易类型:pay.weixin.jspay
     */
    @XmlElement(name = "trade_type")
    private String tradeType;

    /**
     * 用户是否关注公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    @XmlElement(name = "is_subscribe")
    private String isSubscribe;

    /**
     * 支付结果：0—成功；其它—失败
     */
    @XmlElement(name = "pay_result")
    private Integer payResult;

    /**
     * 支付结果信息,支付成功时为空
     */
    @XmlElement(name = "pay_info")
    private String payInfo;

    /**
     * 平台交易号
     */
    @XmlElement(name = "transaction_id")
    private String transactionId;

    /**
     * 第三方订单号
     */
    @XmlElement(name = "out_transaction_id")
    private String outTransactionId;

    /**
     * 用户是否关注子公众账号，Y-关注，N-未关注，仅在公众账号类型支付有效
     */
    @XmlElement(name="sub_is_subscribe")
    private String subIsSubscribe;

    /**
     * 子商户appid
     */
    @XmlElement(name="sub_appid")
    private String subAppid;

    /**
     * 用户openid:用户在商户 sub_appid 下的唯一标识
     */
    @XmlElement(name="sub_openid")
    private String subOpenid;

    /**
     * 商户订单号:商户系统内部的定单号
     */
    @XmlElement(name = "out_trade_no")
    private String outTradeNo;

    /**
     * 总金额，以分为单位，不允许包含任何字、符号
     */
    @XmlElement(name = "total_fee")
    private Integer totalFee;

    /**
     * 现金券支付金额<=订单总金额， 订单总金额-现金券金额为现金支付金额
     */
    @XmlElement(name = "coupon_fee")
    private Integer couponFee;

    /**
     * 货币类型，符合 ISO 4217 标准的三位字母代码，默认人民币：CNY
     */
    @XmlElement(name = "fee_type")
    private String feeType;

    /**
     * 附加信息:商家数据包，原样返回
     */
    @XmlElement(name = "attach")
    private String attach;

    /**
     * 付款银行
     */
    @XmlElement(name = "bank_type")
    private String bankType;

    /**
     * 银行订单号
     */
    @XmlElement(name = "bank_billno")
    private String bankBillno;

    /**
     * time_end
     */
    @XmlElement(name = "time_end")
    private String timeEnd;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getIsSubscribe() {
        return isSubscribe;
    }

    public void setIsSubscribe(String isSubscribe) {
        this.isSubscribe = isSubscribe;
    }

    public Integer getPayResult() {
        return payResult;
    }

    public void setPayResult(Integer payResult) {
        this.payResult = payResult;
    }

    public String getPayInfo() {
        return payInfo;
    }

    public void setPayInfo(String payInfo) {
        this.payInfo = payInfo;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getOutTransactionId() {
        return outTransactionId;
    }

    public void setOutTransactionId(String outTransactionId) {
        this.outTransactionId = outTransactionId;
    }

    public String getSubIsSubscribe() {
        return subIsSubscribe;
    }

    public void setSubIsSubscribe(String subIsSubscribe) {
        this.subIsSubscribe = subIsSubscribe;
    }

    public String getSubAppid() {
        return subAppid;
    }

    public void setSubAppid(String subAppid) {
        this.subAppid = subAppid;
    }

    public String getSubOpenid() {
        return subOpenid;
    }

    public void setSubOpenid(String subOpenid) {
        this.subOpenid = subOpenid;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
        this.totalFee = totalFee;
    }

    public Integer getCouponFee() {
        return couponFee;
    }

    public void setCouponFee(Integer couponFee) {
        this.couponFee = couponFee;
    }

    public String getFeeType() {
        return feeType;
    }

    public void setFeeType(String feeType) {
        this.feeType = feeType;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankBillno() {
        return bankBillno;
    }

    public void setBankBillno(String bankBillno) {
        this.bankBillno = bankBillno;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object o) {
        return EqualsBuilder.reflectionEquals(this,o,false);
    }

    @Override
    public int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this,false);
    }

}
