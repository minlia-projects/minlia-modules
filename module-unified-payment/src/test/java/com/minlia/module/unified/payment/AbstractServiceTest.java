package com.minlia.module.unified.payment;

import com.minlia.module.unified.payment.alipay.v1.AlipayConfig;
import com.minlia.module.unified.payment.alipay.v1.AlipayCreatePreOrderService;
import com.minlia.module.unified.payment.config.Certificate;
import com.minlia.module.unified.payment.wechat.v1.WechatConfig;
import com.minlia.module.unified.payment.wechat.v1.WechatCreatePreOrderService;

/**
 * Created by will on 9/14/17.
 */
public class AbstractServiceTest {


    public static final String DEFAULT_CALLBACK = "http://v8.s1.natapp.cc/api/open/callback/unified/payment";


//  Bible Code: System.Unified.Payment.Alipay
//  Bible Item code: AppId:
    //                AppPrivateKey:
    //                AppPrivateKey:
    //                AppPublicKey:
    //                PublicKey::

    public static final String ALIPAY_APP_ID = "2017082408355971";
    public static final String ALIPAY_APP_PRIVATE_KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDOxZtSxrmZtUCLH6OCHdZy/ZrBmUW51pCZJU2I5fFspP57neMOcGdegRz1wWzjGfcGh6MGp5MrpZJnV2thSG2TDj3GjLpbqaYBhmYuLxJpFjMDzliJcywbnjnqpun2KkGUASzW/RjoBfNldikxBdcipDAK6+jzw6VN5RhvrLERBpHcFTxII8wU+EOwApunCE1IfxrBzGgbjT7bDU/kEeH2GJHrQZ1DOmlU8b0Xe+Z8Fb0O8wLWimH38lPlaJLrbp2fKqtDWMHnJhO4tDe8WHVEi7jGQX63EYkYt4i0K/JxCkuH5JzCycmqVJs/h579/WiMcgfRhkn86wxowW0JU9UFAgMBAAECggEASWol9OT0HxwYt4zbPCal7pwWp2hpNkt1ebqh5dpmtsF6TZ/Ib2b/fLFw+9zacWPVrm7YNlOnUFbwPVrvmFHj/dOPoYBz5nm8pEv42asC6WblZBOOofbPkVrDi4oKBcoJEKi+J/MjNbZvLfYfN/Wv5e/P0wDh7DLp7VdLLtNs5vL/tOsps+R72PRMMW9+qgEGMd8pc/GLTNoL+JMeoNCz8KTS8AqLkZf4TG/4Yr6KaFXuafhTewYnFDtFaU4Edql6UEtc2Iy1gB0VG7l6wAt0tZtnhii9Uyv2Pvzt1+XM6kFDyISNvmBDHgEANc3gr3Gid8loUCWeDKut/gHkSR1j3QKBgQDvINmxbCozN++MHkvPdvwXcmmy2RkAqPWU8VBw2cPhXThD+91inqxLCb9BMpJEI4xuZNwCDELVFQbZ+a7TFhyf9mAGUDXw39jGISIiYlu8MFEXx06eSYe+7YRdP9MXn+sPaJ6IyvErMiI701aG53wuyLVoBTpj7wviPixhQIGfgwKBgQDdXFUz48Xvip5F/Wy7jvw+mxaXK+CppBBEWVPhE4gSg+X/OBBYTVD18h+9KOeCV9QZmp/OFxY6cit/2vy4OT0qL5xY7bS+S2KtVN8Z/Q2UkxEsPS7MRgy9NY/F/4X2yzN6SNWuXh5U7knTfky5ol3d8HMIRpIhuX2/cRJf+ElK1wKBgQCg03o1/qZfLjaZCWM5VC/14Qg/tg0/yWLp2macj4lTT+vgpUte4ZLFIPgVdFq1Iyd2s7TYV8C3KN4jye6Pn2Z/Gye390hgCVz03CHl+Dr0VZakpHI2uQNoILyOLrfdQrvH8Df3jm4m/B/UFq4yKwv1Nx3yqNl9jq9yaRhWDBlh2wKBgH3gx8LeP+zcEu0SFAcEZITlhI8qsHNHjMiRU5WmmbaNsvjTVNgh/2wc59eVrzD76O02dLGph0ZtLjdivFu2kQWwVWRsqF+eVtribIxGzrYye42ArWAUZaIjF0Ms721MWFWtnXWup+j8KJdQNylJH7/ZwFBwcSEkblGzZxOUCXm5AoGAPjs2gD3+sSW+FArwP+pA7rSK0bAkzGdR3SsmcYQsy4i1uYnpOR7NrGiJ0D+isgjA1go0+a6ZU/Jp8WPjzWIru2XTWB8lBL1T0EOwEW5xvEf/1QAWZdhcBwphIGfWEQKnamygJAnPZ89DR8bGv+ZF7ByFL2AZlt2SJO3mvpBHTio=";
    public static final String ALIPAY_APP_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzsWbUsa5mbVAix+jgh3Wcv2awZlFudaQmSVNiOXxbKT+e53jDnBnXoEc9cFs4xn3BoejBqeTK6WSZ1drYUhtkw49xoy6W6mmAYZmLi8SaRYzA85YiXMsG5456qbp9ipBlAEs1v0Y6AXzZXYpMQXXIqQwCuvo88OlTeUYb6yxEQaR3BU8SCPMFPhDsAKbpwhNSH8awcxoG40+2w1P5BHh9hiR60GdQzppVPG9F3vmfBW9DvMC1oph9/JT5WiS626dnyqrQ1jB5yYTuLQ3vFh1RIu4xkF+txGJGLeItCvycQpLh+ScwsnJqlSbP4ee/f1ojHIH0YZJ/OsMaMFtCVPVBQIDAQAB";
    public static final String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAzsWbUsa5mbVAix+jgh3Wcv2awZlFudaQmSVNiOXxbKT+e53jDnBnXoEc9cFs4xn3BoejBqeTK6WSZ1drYUhtkw49xoy6W6mmAYZmLi8SaRYzA85YiXMsG5456qbp9ipBlAEs1v0Y6AXzZXYpMQXXIqQwCuvo88OlTeUYb6yxEQaR3BU8SCPMFPhDsAKbpwhNSH8awcxoG40+2w1P5BHh9hiR60GdQzppVPG9F3vmfBW9DvMC1oph9/JT5WiS626dnyqrQ1jB5yYTuLQ3vFh1RIu4xkF+txGJGLeItCvycQpLh+ScwsnJqlSbP4ee/f1ojHIH0YZJ/OsMaMFtCVPVBQIDAQAB";

// Bible Code: System.Unified.Payment.Wechat
// Bible Item code: AppId

    public static final String WECHAT_APPID = "wxc2be3132a353e6d2";
    public static final String WECHAT_MCH_ID = "1488903742";
    public static final String WECHAT_KEY = "04cf3593f0538608a0a9186dfcafa521";


//  public static final String WECHAT_APP_SECRET="e59c42e82bcd86023c2cf63a9fb00206";


    public AlipayConfig alipayCredential() {
        //从Bible里读取出来相关的配置信息
        AlipayConfig config = new AlipayConfig();

        //设置所有参数
        config.setAppId(ALIPAY_APP_ID);
        config.setCallback(DEFAULT_CALLBACK + "/alipay");
        Certificate certificate = new Certificate();
        certificate.setAppPrivateKey(ALIPAY_APP_PRIVATE_KEY);
        certificate.setAppPublicKey(ALIPAY_APP_PUBLIC_KEY);
        certificate.setPlatformPublicKey(ALIPAY_PUBLIC_KEY);
        config.setCertificate(certificate);
        return config;
    }


//  WechatPay = {
//    appid: "wxc2be3132a353e6d2",
//        mch_id: "1488903742",
//        key: "04cf3593f0538608a0a9186dfcafa521",
//        APPSECRET: "e59c42e82bcd86023c2cf63a9fb00206",
//        notify_url: "http://v8.s1.natapp.cc/api/open/callback/unified/payment",
//        trade_type: "APP",
//        appAndroidSignature: "43223a302a5badd9e0fe94f99dd3781f"
//  }


    public WechatConfig wechatCredential() {
        //从Bible里读取出来相关的配置信息
        WechatConfig config = new WechatConfig();

        //设置所有参数


        //设置所有参数
        config.setAppId(WECHAT_APPID);
        config.setMchId(WECHAT_MCH_ID);
        config.setKey(WECHAT_KEY);

        config.setCallback(DEFAULT_CALLBACK + "/wechat");
        Certificate certificate = new Certificate();
        config.setCertificate(certificate);

        //设置所有参数
        return config;
    }


    public WechatCreatePreOrderService wechatCreatePreOrderService() {
        WechatCreatePreOrderService service = new WechatCreatePreOrderService(wechatCredential());
        return service;
    }

    public AlipayCreatePreOrderService alipayCreatePreOrderService() {
        AlipayCreatePreOrderService service = new AlipayCreatePreOrderService(alipayCredential());
        return service;
    }


}
