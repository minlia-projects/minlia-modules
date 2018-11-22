package com.minlia.module.pooul.bean.qo;

import com.minlia.module.pooul.enumeration.SettledStatusEnum;
import com.minlia.module.pooul.enumeration.TradeStateEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 支付订单
 * Created by garen on 2018/8/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulOrderQO {

    /**
     * 与发起支付商户主体一致的小程序APPID
     */
    private String subAppid;

    /**
     * 用户在商户appid下的唯一标识
     */
    private String subOpenid;

    /**
     * 商户订ID
     */
    private String merchantId;

    /**
     * 商户订单号，在同一个merchant_id 下每次请求必须为唯一，如：alextest.scan.113
     */
    private String mchTradeId;

    /**
     * 支付状态
     */
    private TradeStateEnum payStatus;

    /**
     * 结算状态
     */
    private SettledStatusEnum settledStatus;

    /**
     * 支付类型，不同的支付类型，pay_type值不一样。wechat.jsminipg：微信小程序支付
     * 必填
     */
    private String payType;

    /**
     * 支付总金额，单位为分，只能为整数，如：888 代表8.88元
     * 必填
     */
    private Integer totalFee;

    /**
     * 发起支付的终端IP，APP、jsapi、jsminipg、wap支付提交用户端ip，scan、micro支付填调用支付API的服务端IP。
     */
    private String spbillCreateIp;

    /**
     * 终端设备号(门店号或收银设备ID)，注意：PC网页或APP支付请传"WEB"
     */
    private String deviceInfo;

    /**
     * 操作员或收银员编号
     */
    private String opUserId;

    private String createBy;

    private Integer gtCreateDateMinutes;

    private Integer gtCreateDateHour;

    private Integer gtCreateDateDay;

}
