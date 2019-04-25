package com.minlia.module.unified.payment;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.WxPayService;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;
import com.minlia.module.unified.payment.alipay.v1.AlipayConfig;
import com.minlia.module.unified.payment.wechat.v1.WechatConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by will on 9/14/17.
 */
@EnableConfigurationProperties({WechatConfig.class, AlipayConfig.class})
@Configuration
public class CreatePreOrderAutoConfiguration {

    @Bean
    public WxPayService wxPayService(WechatConfig wechatConfig) {
        if (null != wechatConfig.getAppId() && null != wechatConfig.getMchId() && null != wechatConfig.getKey()) {
            WxPayService wxPayService = new WxPayServiceImpl();
            WxPayConfig config = new WxPayConfig();
            config.setAppId(wechatConfig.getAppId());
            config.setMchId(wechatConfig.getMchId());
            config.setMchKey(wechatConfig.getKey());
            config.setNotifyUrl(wechatConfig.getCallback());
            config.setKeyPath(wechatConfig.getKeyPath());
            wxPayService.setConfig(config);
            return wxPayService;
        } else {
            return null;
        }
    }

//
//    // 注意
////    public static final String DEFAULT_CALLBACK = "http://pre-test.wanquancaifu.com/api/open/callback/unified/payment";
////    public static final String DEFAULT_CALLBACK = "http://moff.chinfan.io/api/open/callback/unified/payment";
////    public static final String DEFAULT_CALLBACK = "http://pre-test.wanquancaifu.com/api/open/callback/unified/payment";
//    public static final String DEFAULT_CALLBACK = "http://lmmgqiwhtlrnzagr.dev.chinfan.io/api/open/callback/unified/payment";
//    //public static final String DEFAULT_CALLBACK = "http://pre-test.wanquancaifu.com/api/open/callback/unified/payment";
//    public static final String APP_ID = "2017082408355971";
//    public static final String APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCnSGjloR+Fgr49VpCbTfzBNoauAdBhDXZXN2Mrk0Fydu5yZ+KxyVWOjZRLlx6pR5AAVsShRINbuoG/cpFLWzxY4tXuz98VD+XO+7Q18gdN3ycsi8v1gBls9st+p5Lu0QfLru5dyZNyDnGCaX1cj7/X29YS+roPVP3YgXlHzuNZ8/aKyg/D76KIYsclsRivWX8tabg7cxmFY3m2WdWmabrL7rFZCb01ZLoS/jGHkZbbCC8kafgQx2BkjDiMo4HaCWjWnWOZ09eEc2Q+xTbtzAY+5LgxW0Y0+CFCF/tWg5Y8rAlMwsf7LgsT2AmAMYJL5El3QwL5lQBmDukaLuJxlBGNAgMBAAECggEBAIi03jS6xf3f6QRLmAWzaBmmK85MYTTPQaTo+LyFrPiURS1txI8yUB8diNF1gHMqrn34aty4FvmBo8MHJ8IDn3+scNSnQFIAGfhbLtzfHCUBdwwqetX4h87KqWaqH6PXY52LKfWvZDXTo+PFvdNFDqyjVYFRMXiSJpkmxixu4NGDAfhjiKx3sxPLdRg9IUUYlNHWhSJTM/Ne+tk5+oYc1AIYz+AB/2WwPpVPJNgRprN63lYeZUycLSm+z/3/qPMiSIeRnlH1g9zcV3RFFf/t4EZg3sHE9IgfkwlEpwD9QB6UEbI7M3GQZAso63EPJulsY1Le8q8rJxqeQ3Y0tlVYLMECgYEA0UZC9wa8OmlnxaMjd+gyJkbEFIwRdL4dkgl37jS6A56zd174C3VYsQE4uge+32TSlpFX8vPlo/WLF8dn0+gp854l9MKkpEVwOaEsvzIH8cia/AzMSh7Q/toYLksIhCH1DkAFvzBvC8rl28cqGOGfzI3WhpEINQfs2A4IAx5wrokCgYEAzKH8awns5+iJB9IjDIi9KvIB1CsBmIKyWXkt8YAf6uF+yQPgxT50//1kLTvIvf7Fq+stzabqzzCvRPDJ9kKoAOYDXmtBfSGpu9MxxCidFbaSXUTcu4i5yOAXDIXuG3HtYj7f+gmj7QmeJUz92ijpN7Zkg+mH11Zr4HtXGT80KeUCgYA8G/pQCdc2z9/GnL0rS75b0Cex3ymEZrcHo0CXYv2q3IKf3t9QKzx2Wut4Q5FHiSYTqVuhpPxow7ZX9iaUbzLdjqdITkAMDwXVB75ajHxLrlsV2nczMvbEvNAFO/gOO9KvbSQ5bif3MygioGovC19i2bqsnCvHT6dvx1f1zWCAQQKBgGyXQXYj7D4c3iryTnsmTQxvJJ1MvSoLoU1tyv2vOkDQrRFLJ4pEXJ5D/1cptJtuicNzp/jMLSsJ8Niq1uIB+VmQthTy4evfY00zA+POkZ2mJwGru18hbUW19UVGBVc3G/vY1c7AMudlBTFSLBwLPce82JZP5TBWwiR8D3psWegpAoGAFNaTyzqB9fd4Tza6oBkTwzqDMth4CsGN1jH9YONJo9TlVHrFdRqf7bVyj1n58L+RDkhLkZY6/Snp079MhB+jdYRZgFeFcCcJ0OLr5Eae5MMCCFoZBfQLpc5B4Eu6NC1PLDuZ8Q/RGy3v0ACsj4EJ+z/cOHY+VTO8g3XATuhczE8=";
//    public static final String APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAp0ho5aEfhYK+PVaQm038wTaGrgHQYQ12VzdjK5NBcnbucmfisclVjo2US5ceqUeQAFbEoUSDW7qBv3KRS1s8WOLV7s/fFQ/lzvu0NfIHTd8nLIvL9YAZbPbLfqeS7tEHy67uXcmTcg5xgml9XI+/19vWEvq6D1T92IF5R87jWfP2isoPw++iiGLHJbEYr1l/LWm4O3MZhWN5tlnVpmm6y+6xWQm9NWS6Ev4xh5GW2wgvJGn4EMdgZIw4jKOB2glo1p1jmdPXhHNkPsU27cwGPuS4MVtGNPghQhf7VoOWPKwJTMLH+y4LE9gJgDGCS+RJd0MC+ZUAZg7pGi7icZQRjQIDAQAB";
//    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAyV+YxRFGk7qXK4XW9oSTWszgSjCctw7rTlQ/5uPgG43+3kL8teIKtQCZLpnEu6DzOxNW+sF3N1bNKCJG/BxJjPmysyKKGDQ4LrjJcOPe1r0RXxv4FBRQcFBe/kDzFmFdM5UQjlmHQObSIvK4HP81kGG7ylGtZW3mKuxLAQ8ToPPURzE/q2xwgHm7SC9qkYe+9hi03gK+cSsyJF2KBA+/9/4EtkX5P7uzNdKmLrJ5opXcNHjFQ5a5+tqPXaDKRTPO1u0n8+sqJKV06dLFhblsevbEr4D90XnTNPjQ+JOR5lb5IpvpyMaY7ditkKdbC6KXGlhc1Q5Ikyt2vE9ufjelnwIDAQAB";
//
//    @Bean
//    @Lazy
//    public AlipayConfig alipayCredential() {
//        //从Bible里读取出来相关的配置信息
//
//        //设置所有参数
//        Certificate certificate = new Certificate();
//        certificate.setAppPublicKey(APP_PUBLIC_KEY);            //应用公钥
//        certificate.setAppPrivateKey(APP_PRIVATE_KEY);          //应用私钥
//        certificate.setPlatformPublicKey(ALIPAY_PUBLIC_KEY);    //平台公钥
//
//        AlipayConfig config = new AlipayConfig();
//        config.setAppId(APP_ID);
//        config.setCallback(DEFAULT_CALLBACK + "/alipay");
//        config.setCertificate(certificate);
//        return config;
//    }
//
//    public static final String WECHAT_APPID = "wxc2be3132a353e6d2";
//    public static final String WECHAT_MCH_ID = "1488903742";
//    public static final String WECHAT_KEY = "04cf3593f0538608a0a9186dfcafa521";
//
//    @Bean
//    @Lazy
//    public WechatConfig wechatCredential() {
//        //从Bible里读取出来相关的配置信息
//        WechatConfig config = new WechatConfig();
//        config.setAppId(WECHAT_APPID);
//        config.setMchId(WECHAT_MCH_ID);
//        config.setKey(WECHAT_KEY);
//        config.setCallback(DEFAULT_CALLBACK + "/wechat");
//        Certificate certificate = new Certificate();
//        config.setCertificate(certificate);
//        return config;
//    }
//
//    @Bean
//    @Lazy
//    public WechatCreatePreOrderService wechatCreatePreOrderService() {
//        WechatCreatePreOrderService service = new WechatCreatePreOrderService(wechatCredential());
//        return service;
//    }
//
//    @Bean
//    @Lazy
//    public AlipayCreatePreOrderService alipayCreatePreOrderService() {
//        AlipayCreatePreOrderService service = new AlipayCreatePreOrderService(alipayCredential());
//        return service;
//    }

}
