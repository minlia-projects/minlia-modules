//package com.minlia.module.unified.payment.sdk;
//
//import com.minlia.module.unified.payment.sdk.alipay.v1.AlipayConfig;
//import com.minlia.module.unified.payment.sdk.alipay.v1.AlipayCreatePreOrderService;
//import com.minlia.module.unified.payment.sdk.wechat.v1.WechatConfig;
//import com.minlia.module.unified.payment.sdk.wechat.v1.WechatCreatePreOrderService;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//
//@Configuration
//public class UnifiedPaymentConfiguration {
//
//
//  @Bean
//  @Lazy
//  public AlipayConfig alipayCredential() {
//    //从Bible里读取出来相关的配置信息
//    AlipayConfig config = new AlipayConfig();
//
//    //设置所有参数
////    credential.setAppId("");
////    credential.setCertificate();
//
//    return config;
//  }
//
//  @Bean
//  @Lazy
//  public WechatConfig wechatCredential() {
//    //从Bible里读取出来相关的配置信息
//    WechatConfig config = new WechatConfig();
//
//    //设置所有参数
////    credential.setAppId("");
////    credential.setCertificate();
//
//    //设置所有参数
//    return config;
//  }
//
//
//  @Bean
//  @Lazy
//  public WechatCreatePreOrderService wechatCreatePreOrderService() {
//    WechatCreatePreOrderService service = new WechatCreatePreOrderService(wechatCredential());
//    return service;
//  }
//
//  @Bean
//  @Lazy
//  public AlipayCreatePreOrderService alipayCreatePreOrderService() {
//    AlipayCreatePreOrderService service = new AlipayCreatePreOrderService(alipayCredential());
//    return service;
//  }
//}
//
