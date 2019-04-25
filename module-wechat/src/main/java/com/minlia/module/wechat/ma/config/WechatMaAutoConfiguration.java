package com.minlia.module.wechat.ma.config;

import cn.binarywang.wx.miniapp.api.WxMaQrcodeService;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.api.impl.WxMaQrcodeServiceImpl;
import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.config.WxMaConfig;
import cn.binarywang.wx.miniapp.config.WxMaInMemoryConfig;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.wechat.ma.constant.WechatMaBibleConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass(WxMaService.class)
@EnableConfigurationProperties(value = {WechatMaProperties.class})
public class WechatMaAutoConfiguration {

    @Autowired
    private BibleItemService bibleItemService;

    @Bean
    @ConditionalOnMissingBean
    public WxMaConfig wxMaConfig() {
        WxMaInMemoryConfig wxMaConfig = new WxMaInMemoryConfig();
        wxMaConfig.setAppid(bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_CODE_APPID));
        wxMaConfig.setSecret(bibleItemService.get(WechatMaBibleConstants.MINIAPP_CODE, WechatMaBibleConstants.MINIAPP_ITEM_CODE_SECRET));
        wxMaConfig.setExpiresTime(System.currentTimeMillis());
        return wxMaConfig;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaService wxMaService(WxMaConfig config) {
        WxMaService wxMaService = new WxMaServiceImpl();
        wxMaService.setWxMaConfig(config);
        return wxMaService;
    }

    @Bean
    @ConditionalOnMissingBean
    public WxMaQrcodeService wxMaQrcodeService(WxMaService wxMaService) {
        WxMaQrcodeService wxMaQrcodeService = new WxMaQrcodeServiceImpl(wxMaService);
        return wxMaQrcodeService;
    }

}
