package com.minlia.module.unified.payment.body;

import com.minlia.cloud.body.Body;
import lombok.Data;

/**
 * Created by will on 9/15/17.
 * 定义本系统的通知体
 */
@Data
public class OrderPaidNotificationBody implements Body {

    /**
     * 付款人
     */
    private String paidBy;//openid buyerId

    /**
     * 付款金额
     */
    private Integer amount;//total_fee receiptAmount

    private String subject;//付款主题, 为买什么而支付

    private String body;//付款备注  attach bean

    private String sign;//sign

    private String gatewayTradeNo;//transaction_id  tradeNo

    /**
     * 订单号
     */
    private String merchantTradeNo;//out_trade_no outTradeNo

    /**
     * 支付类型
     */
    private PayType payType;


//  private String appid;//appid appId

//wechat
//  <xml><appid><![CDATA[wxc2be3132a353e6d2]]></appid>
// <bank_type><![CDATA[CFT]]></bank_type>
// <cash_fee><![CDATA[1]]></cash_fee>
// <fee_type><![CDATA[CNY]]></fee_type>
// <is_subscribe><![CDATA[N]]></is_subscribe>
// <mch_id><![CDATA[1488903742]]></mch_id>
// <nonce_str><![CDATA[fBzjXWrfZLtyx4AWTpMdpwJOsGyyyseH]]></nonce_str>
// <openid><![CDATA[owaY71pzMDLQcm-IHOIf-TmAsk2Q]]></openid>
// <out_trade_no><![CDATA[2017815134232152]]></out_trade_no>
// <result_code><![CDATA[SUCCESS]]></result_code>
// <return_code><![CDATA[SUCCESS]]></return_code>
// <sign><![CDATA[]]></sign>
// <time_end><![CDATA[20170915134240]]></time_end>
// <total_fee>1</total_fee>
// <trade_type><![CDATA[APP]]></trade_type>
// <transaction_id>642001201709152213366749]]></transaction_id>
// </xml>

//<xml>
// <appid><![CDATA[wx62eb98733173fa88]]></appid>
// <attach><![CDATA[TC100000000001149244308520600102]]></attach>
// <bank_type><![CDATA[CFT]]></bank_type>
// <cash_fee><![CDATA[1]]></cash_fee>
// <fee_type><![CDATA[CNY]]></fee_type>
// <is_subscribe><![CDATA[Y]]></is_subscribe>
// <mch_id><![CDATA[1402784402]]></mch_id>
// <nonce_str><![CDATA[rjiNOHeUcwUjCwiOZIQz]]></nonce_str>
// <openid><![CDATA[oGM7bwGdkotqW25z8Bwm05hrXDZY]]></openid>
// <out_trade_no><![CDATA[TC100000000001149244308520600102]]></out_trade_no>
// <result_code><![CDATA[SUCCESS]]></result_code>
// <return_code><![CDATA[SUCCESS]]></return_code>
// <sign><![CDATA[33171538931F694227137AC6521EE01A]]></sign>
// <sub_appid><![CDATA[wxea293eca23602b9c]]></sub_appid>
// <sub_is_subscribe><![CDATA[Y]]></sub_is_subscribe>
// <sub_mch_id><![CDATA[1415606702]]></sub_mch_id>
// <sub_openid><![CDATA[o4OzlvmDyhMKZ6-RdrRxz5WaRgo0]]></sub_openid>
// <time_end><![CDATA[20170417233138]]></time_end>
// <total_fee>1</total_fee>
// <trade_type><![CDATA[JSAPI]]></trade_type>
// <transaction_id><![CDATA[4004642001201704177391333080]]></transaction_id>
// </xml>

    //alipay
//{
// charset=UTF-8,
// subject=支付主题,
// sign=Qa+/QDeWc1vC1ihfnGsd++/9ovlTOR6//+92rM8MP6///==,
// invoiceAmount=0.01,
// bean=备注内容,
// buyerId=2088002650456370,
// buyerLogonId=mmi***@live.com,
// receiptAmount=0.01,
// sellerId=2088721842507129,
// gmtPayment=2017-09-15 13:45:39,
// appId=2017082408355971,
// outTradeNo=AO412048142287,
// signType=RSA2,
// fundBillList=[{"amount":"0.01","fundChannel":"ALIPAYACCOUNT"}],
// pointAmount=0.00,
// tradeNo=2017091521001004370223857095,
// notifyTime=2017-09-15 13:45:40,
// gmtCreate=2017-09-15 13:45:39,
// sellerEmail=moffvape@gmail.com,
// version=1.0, totalAmount=0.01,
// notifyType=trade_status_sync,
// tradeStatus=TRADE_SUCCESS, buyerPayAmount=0.01,
// notifyId=d0ee51e133fcf51fd8681589b16ae12iuu,
// authAppId=2017082408355971
// }


}
