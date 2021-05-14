package com.minlia.module.pay.config;

import com.egzosn.pay.common.bean.CertStoreType;
import com.egzosn.pay.common.http.HttpConfigStorage;
import com.egzosn.pay.spring.boot.core.PayServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.MerchantDetailsServiceConfigurer;
import com.egzosn.pay.spring.boot.core.configurers.PayMessageConfigurer;
import com.egzosn.pay.spring.boot.core.merchant.PaymentPlatform;
import com.egzosn.pay.spring.boot.core.merchant.bean.AliMerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.bean.PaypalMerchantDetails;
import com.egzosn.pay.spring.boot.core.merchant.bean.WxMerchantDetails;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.AliPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaymentPlatforms;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.PaypalPaymentPlatform;
import com.egzosn.pay.spring.boot.core.provider.merchant.platform.WxPaymentPlatform;
import com.minlia.cloud.holder.ContextHolder;
import com.minlia.module.pay.entity.MerchantDetailsEntity;
import com.minlia.module.pay.handler.AliPayMessageHandler;
import com.minlia.module.pay.handler.PayPalPayMessageHandler;
import com.minlia.module.pay.handler.WxPayMessageHandler;
import com.minlia.module.pay.interceptor.AliPayMessageInterceptor;
import com.minlia.module.pay.interceptor.PaypalPayMessageInterceptor;
import com.minlia.module.pay.interceptor.WxPayMessageInterceptor;
import com.minlia.module.pay.service.MerchantDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * 支付服务配置
 *
 * @author egan
 * email egzosn@gmail.com
 * date 2019/5/26.19:25
 */
@Slf4j
@Configuration
public class MerchantPayServiceConfigurer implements PayServiceConfigurer {

    //    @Autowired
//    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AutowireCapableBeanFactory spring;

    /**
     * 商户配置
     *
     * @param merchants 商户配置
     */
    @Override
    public void configure(MerchantDetailsServiceConfigurer merchants) {
//        数据库文件存放 /doc/sql目录下
//        merchants.jdbc(jdbcTemplate);
        log.info("初始化支付商户配置==============================================");
        MerchantDetailsService merchantDetailsService = ContextHolder.getContext().getBean(MerchantDetailsService.class);
        List<MerchantDetailsEntity> merchantDetailsEntities = merchantDetailsService.list();

        //微信请求配置，详情参考https://gitee.com/egzosn/pay-java-parent项目中的使用
        HttpConfigStorage wxHttpConfigStorage = new HttpConfigStorage();
        wxHttpConfigStorage.setMaxTotal(20);
        wxHttpConfigStorage.setDefaultMaxPerRoute(10);
//        wxHttpConfigStorage.setCertStoreType(CertStoreType.URL);
//        wxHttpConfigStorage.setKeystore("http://www.egzosn.com/certs/ssl 退款证书");
//        wxHttpConfigStorage.setStorePassword("ssl 证书对应的密码， 默认为商户号");

        //内存Builder方式
        merchants.inMemory()
                .ali()
                .detailsId(merchantDetailsEntities.get(0).getDetailsId())
                .appid(merchantDetailsEntities.get(0).getAppid())
                .keyPrivate(merchantDetailsEntities.get(0).getKeyPrivate())
                .keyPublic(merchantDetailsEntities.get(0).getKeyPublic())
                .inputCharset(merchantDetailsEntities.get(0).getInputCharset())
                .notifyUrl(merchantDetailsEntities.get(0).getNotifyUrl())
                .returnUrl(merchantDetailsEntities.get(0).getReturnUrl())
                .pid(merchantDetailsEntities.get(0).getMchId())
                .seller(merchantDetailsEntities.get(0).getSeller())
                .signType(merchantDetailsEntities.get(0).getSignType())
                .test(merchantDetailsEntities.get(0).getIsTest())
                .and()
                .wx()
                .detailsId(merchantDetailsEntities.get(1).getDetailsId())
                .appid(merchantDetailsEntities.get(1).getAppid())
                .mchId(merchantDetailsEntities.get(1).getMchId())
                .secretKey(merchantDetailsEntities.get(1).getKeyPrivate())
                .notifyUrl(merchantDetailsEntities.get(1).getNotifyUrl())
                .returnUrl(merchantDetailsEntities.get(1).getReturnUrl())
                .inputCharset(merchantDetailsEntities.get(1).getInputCharset())
                .signType(merchantDetailsEntities.get(1).getSignType())
                //设置请求相关的配置
                .httpConfigStorage(wxHttpConfigStorage)
                .test(merchantDetailsEntities.get(1).getIsTest())
                .and()
                .addMerchantDetails(paypalMerchantDetails(merchantDetailsEntities.get(2)));
        ;

//        merchants.inMemory().addMerchantDetails(aliMerchantDetails(merchantDetailsEntities.get(0)));
//        merchants.inMemory().addMerchantDetails(wxMerchantDetails(merchantDetailsEntities.get(1)));
//        merchants.inMemory().addMerchantDetails(paypalMerchantDetails(merchantDetailsEntities.get(2)));

      /*  //------------内存手动方式------------------
        UnionMerchantDetails unionMerchantDetails = new UnionMerchantDetails();
        unionMerchantDetails.detailsId("3");
        //内存方式的时候这个必须设置
        unionMerchantDetails.setCertSign(true);
        unionMerchantDetails.setMerId("700000000000001");
        //公钥，验签证书链格式： 中级证书路径;
        unionMerchantDetails.setAcpMiddleCert("D:/certs/acp_test_middle.cer");
        //公钥，根证书路径
        unionMerchantDetails.setAcpRootCert("D:/certs/acp_test_root.cer");
        //私钥, 私钥证书格式： 私钥证书路径
        unionMerchantDetails.setKeyPrivateCert("D:/certs/acp_test_sign.pfx");
        //私钥证书对应的密码
        unionMerchantDetails.setKeyPrivateCertPwd("000000");
        //证书的存储方式
        unionMerchantDetails.setCertStoreType(CertStoreType.PATH);
        unionMerchantDetails.setNotifyUrl("http://127.0.0.1/payBack4.json");
        // 无需同步回调可不填  app填这个就可以
        unionMerchantDetails.setReturnUrl("http://127.0.0.1/payBack4.json");
        unionMerchantDetails.setInputCharset("UTF-8");
        unionMerchantDetails.setSignType("RSA2");
        unionMerchantDetails.setTest(true);
        //手动加入商户容器中
        merchants.inMemory().addMerchantDetails(unionMerchantDetails);*/
    }

    /**
     * 商户配置
     *
     * @param configurer 支付消息配置
     */
    @Override
    public void configure(PayMessageConfigurer configurer) {
        WxPayMessageHandler wxPayMessageHandler = ContextHolder.getContext().getBean(WxPayMessageHandler.class);
        AliPayMessageHandler aliPayMessageHandler = ContextHolder.getContext().getBean(AliPayMessageHandler.class);
        PayPalPayMessageHandler payPalPayMessageHandler = ContextHolder.getContext().getBean(PayPalPayMessageHandler.class);

        PaymentPlatform aliPaymentPlatform = PaymentPlatforms.getPaymentPlatform(AliPaymentPlatform.platformName);
        configurer.addHandler(aliPaymentPlatform, aliPayMessageHandler);
        configurer.addInterceptor(aliPaymentPlatform, spring.getBean(AliPayMessageInterceptor.class));

        PaymentPlatform wxPaymentPlatform = PaymentPlatforms.getPaymentPlatform(WxPaymentPlatform.platformName);
        configurer.addHandler(wxPaymentPlatform, wxPayMessageHandler);
        configurer.addInterceptor(wxPaymentPlatform, spring.getBean(WxPayMessageInterceptor.class));

        PaymentPlatform paypalPaymentPlatform = PaymentPlatforms.getPaymentPlatform(PaypalPaymentPlatform.platformName);
        configurer.addHandler(paypalPaymentPlatform, payPalPayMessageHandler);
        configurer.addInterceptor(paypalPaymentPlatform, spring.getBean(PaypalPayMessageInterceptor.class));
    }

    public AliMerchantDetails aliMerchantDetails(MerchantDetailsEntity merchantDetailsEntity) {
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        httpConfigStorage.setMaxTotal(20);
        httpConfigStorage.setDefaultMaxPerRoute(10);

        AliMerchantDetails details = new AliMerchantDetails();
        details.detailsId(merchantDetailsEntity.getDetailsId());
        details.appid(merchantDetailsEntity.getAppid());
        details.pid(merchantDetailsEntity.getMchId());
        details.seller(merchantDetailsEntity.getSeller());
        details.keyPrivate(merchantDetailsEntity.getKeyPrivate());
        details.keyPublic(merchantDetailsEntity.getKeyPublic());
        details.inputCharset(merchantDetailsEntity.getInputCharset());
        details.notifyUrl(merchantDetailsEntity.getNotifyUrl());
        details.returnUrl(merchantDetailsEntity.getReturnUrl());
        details.signType(merchantDetailsEntity.getSignType());
        details.setTest(merchantDetailsEntity.getIsTest());
        details.httpConfigStorage(httpConfigStorage);
        return details;
    }

    public WxMerchantDetails wxMerchantDetails(MerchantDetailsEntity merchantDetailsEntity) {
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        httpConfigStorage.setMaxTotal(20);
        httpConfigStorage.setDefaultMaxPerRoute(10);

        httpConfigStorage.setKeystore("http://www.egzosn.com/certs/ssl 退款证书");
        httpConfigStorage.setCertStoreType(CertStoreType.URL);
        httpConfigStorage.setStorePassword("ssl 证书对应的密码， 默认为商户号");

        WxMerchantDetails details = new WxMerchantDetails();
        details.detailsId(merchantDetailsEntity.getDetailsId());
        details.appid(merchantDetailsEntity.getAppid());
        details.mchId(merchantDetailsEntity.getMchId());
        details.secretKey(merchantDetailsEntity.getKeyCert());
        details.inputCharset(merchantDetailsEntity.getInputCharset());
        details.notifyUrl(merchantDetailsEntity.getNotifyUrl());
        details.returnUrl(merchantDetailsEntity.getReturnUrl());
        details.signType(merchantDetailsEntity.getSignType());
        details.setTest(merchantDetailsEntity.getIsTest());
        details.httpConfigStorage(httpConfigStorage);
        return details;
    }

    public PaypalMerchantDetails paypalMerchantDetails(MerchantDetailsEntity merchantDetailsEntity) {
        HttpConfigStorage httpConfigStorage = new HttpConfigStorage();
        httpConfigStorage.setMaxTotal(20);
        httpConfigStorage.setDefaultMaxPerRoute(10);

        PaypalMerchantDetails details = new PaypalMerchantDetails();
        details.detailsId(merchantDetailsEntity.getDetailsId());
        details.setClientID(merchantDetailsEntity.getMchId());
        details.setClientSecret(merchantDetailsEntity.getKeyCert());
        details.setNotifyUrl(merchantDetailsEntity.getNotifyUrl());
        details.setReturnUrl(merchantDetailsEntity.getReturnUrl());
        details.httpConfigStorage(httpConfigStorage);
        details.cancelUrl(merchantDetailsEntity.getReturnUrl());
        details.setTest(false);
        details.initService();
        return details;
    }

}