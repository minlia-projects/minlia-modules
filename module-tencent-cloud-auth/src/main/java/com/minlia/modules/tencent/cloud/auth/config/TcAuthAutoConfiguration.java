//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.tencent.cloud.auth.config;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.modules.tencent.cloud.auth.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiTicket;
import com.minlia.modules.tencent.cloud.auth.constant.TcAuthBibleConstants;
import com.minlia.modules.tencent.cloud.auth.service.TcAuthService;
import com.minlia.modules.tencent.cloud.auth.service.TcAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by garen on 2018/4/19.
 */
@Configuration
@ConditionalOnClass(TcAuthService.class)
public class TcAuthAutoConfiguration {

    @Autowired
    private BibleItemService bibleItemService;

    @Bean
//    @ConditionalOnMissingBean
    public TcAuthConfig tcAuthConfig() {
        TcAuthMemoryConfig tcAuthMemoryConfig = new TcAuthMemoryConfig();
        tcAuthMemoryConfig.setAppid(bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE, TcAuthBibleConstants.APPID));
        tcAuthMemoryConfig.setSecret(bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE, TcAuthBibleConstants.SECRET));

        String accessTokenUrl = bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE,TcAuthBibleConstants.ACCESS_TOKEN_URL);
        accessTokenUrl = String.format(accessTokenUrl,tcAuthMemoryConfig.getAppid(),tcAuthMemoryConfig.getSecret());
        accessTokenUrl = accessTokenUrl.replace("\n","");
        tcAuthMemoryConfig.setAccessTokenUrl(accessTokenUrl);

        tcAuthMemoryConfig.setApiTicketUrl(bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE,TcAuthBibleConstants.API_TICKET_URL));

        TcAccessToken accessToken = new TcAccessToken();
        accessToken.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setAccessToken(accessToken);

        TcApiTicket apiTicket = new TcApiTicket();
        apiTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiTicket(apiTicket);

        return tcAuthMemoryConfig;
    }

    @Bean
//    @ConditionalOnMissingBean
    public TcAuthService tcAuthService(TcAuthConfig tcAuthConfig){
        TcAuthService tcAuthService = new TcAuthServiceImpl();
        tcAuthService.setTcAuthConfig(tcAuthConfig);
        return tcAuthService;
    }

}
