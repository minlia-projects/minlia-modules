package com.minlia.module.unified.payment.wechat.v1;

import com.minlia.module.unified.payment.config.Certificate;
import com.minlia.module.unified.payment.config.Config;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by will on 9/14/17.
 * 精减配置, 只配置需要的部分
 */
public class WechatConfig implements Config {

    private Certificate certificate;
    private String appId;
    private String mchId;
    private String key;


    /**
     * 交易通道在创建完之后服务端 Server to Server(S2S)通知回调的入口
     */
    private String callback;


    public WechatConfig() {
        if (null != this.getCertificate()) {
            if (!StringUtils.isEmpty(this.getCertificate().getPlatformPublicKey())) {
                throw new RuntimeException("微信支付无需配置平台公钥");
            }
            if (!StringUtils.isEmpty(this.getCertificate().getAppPublicKey())) {
                throw new RuntimeException("微信支付无需配置应用公钥");
            }
            if (!StringUtils.isEmpty(this.getCertificate().getAppPrivateKey())) {
                throw new RuntimeException("微信支付无需配置应用私钥钥");
            }
            //但是可以配置证书路径
        }
    }

    public Certificate getCertificate() {
        return certificate;
    }

    public void setCertificate(Certificate certificate) {
        this.certificate = certificate;
    }


    @Override
    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }


    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getCallback() {
        return callback;
    }

    public void setCallback(String callback) {
        this.callback = callback;
    }
}
