package com.minlia.module.tencent.miniapp.config;

import lombok.Data;

@Data
//@ConfigurationProperties(prefix = "wechat.ma")
public class WechatMiniAppConfig {

    /**
     * 设置小程序的appid
     */
    private String appId;

    /**
     * 设置小程序的app secret
     */
    private String secret;

}
