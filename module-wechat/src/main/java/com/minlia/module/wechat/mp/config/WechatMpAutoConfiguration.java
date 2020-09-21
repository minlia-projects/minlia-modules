package com.minlia.module.wechat.mp.config;

import cn.binarywang.wx.miniapp.config.impl.WxMaDefaultConfigImpl;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.wechat.mp.constant.WechatMpBibleConstants;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
<<<<<<< HEAD
=======
import me.chanjar.weixin.mp.util.WxMpConfigStorageHolder;
>>>>>>> dev/garen
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(WxMpService.class)
@EnableConfigurationProperties(value = {WechatMpProperties.class})
public class WechatMpAutoConfiguration {

    private final BibleItemService bibleItemService;

    public WechatMpAutoConfiguration(BibleItemService bibleItemService) {
        this.bibleItemService = bibleItemService;
    }

    /**
     * 微信公众号配置
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public WxMpConfigStorage configStorage() {
<<<<<<< HEAD

=======
>>>>>>> dev/garen
        WxMpDefaultConfigImpl configStorage = new WxMpDefaultConfigImpl();
        configStorage.setAppId( bibleItemService.get(WechatMpBibleConstants.PUBLIC_CODE, WechatMpBibleConstants.PUBLIC_ITEM_CODE_APPID));
        configStorage.setSecret(bibleItemService.get(WechatMpBibleConstants.PUBLIC_CODE, WechatMpBibleConstants.PUBLIC_ITEM_CODE_SECRET));
        configStorage.setAesKey(bibleItemService.get(WechatMpBibleConstants.PUBLIC_CODE, WechatMpBibleConstants.PUBLIC_ITEM_CODE_AESKEY));
        configStorage.setToken(bibleItemService.get(WechatMpBibleConstants.PUBLIC_CODE, WechatMpBibleConstants.PUBLIC_ITEM_CODE_TOKEN));
        return configStorage;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMpService wxMpService(WxMpConfigStorage configStorage) {
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(configStorage);
        return wxMpService;
    }

}
