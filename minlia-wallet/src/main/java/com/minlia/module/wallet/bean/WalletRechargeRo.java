//package com.minlia.module.wallet.bean;
//
//import com.minlia.module.unified.payment.enumeration.PayChannelEnum;
//import lombok.Data;
//
//import javax.validation.constraints.DecimalMin;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//import java.math.BigDecimal;
//
///**
// * 充值请求体
// */
//@Data
//public class WalletRechargeRo {
//
//    @NotNull
//    @DecimalMin("0.01")
//    private BigDecimal totalFee;
//
//    @NotNull
//    private PayChannelEnum channel;
//
//    /**
//     * 微信交易类型
//     */
//    private String tradeType;
//
//    @Size(max = 200)
//    private String body;
//
//}
