package com.minlia.module.wechat.ma.config;

import com.minlia.module.bible.service.BibleItemService;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 使用bible替代配置项
 * @see WechatMaAutoConfiguration
 */
@Deprecated
@Data
@ConfigurationProperties(prefix = "wechat.miniapp")
public class WechatMaProperties {
    /**
     * 设置小程序的appid
     */
    private String appId;

    /**
     * 设置小程序的app secret
     */
    private String secret;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
