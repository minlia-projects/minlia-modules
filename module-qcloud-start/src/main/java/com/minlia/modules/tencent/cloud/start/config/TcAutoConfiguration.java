//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.tencent.cloud.start.config;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.modules.tencent.cloud.start.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.start.bean.TcApiNonceTicket;
import com.minlia.modules.tencent.cloud.start.bean.TcApiSignTicket;
import com.minlia.modules.tencent.cloud.start.constant.TcBibleConstants;
import com.minlia.modules.tencent.cloud.start.service.TcService;
import com.minlia.modules.tencent.cloud.start.service.TcServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by garen on 2018/4/19.
 */
@Configuration
@ConditionalOnClass(TcService.class)
public class TcAutoConfiguration {

    @Autowired
    private BibleItemService bibleItemService;

    @Bean
//    @ConditionalOnMissingBean
    public TcConfig tcConfig() {
        String appid = bibleItemService.get(TcBibleConstants.BIBLE_CODE, TcBibleConstants.APPID);
        String secret = bibleItemService.get(TcBibleConstants.BIBLE_CODE, TcBibleConstants.SECRET);

        String accessTokenUrl = bibleItemService.get(TcBibleConstants.BIBLE_CODE, TcBibleConstants.ACCESS_TOKEN_URL);
        accessTokenUrl = String.format(accessTokenUrl,appid,secret);
        accessTokenUrl = accessTokenUrl.replace("\n","");

        TcConfigMemoryConfig tcAuthMemoryConfig = new TcConfigMemoryConfig();
        tcAuthMemoryConfig.setAppid(appid);
        tcAuthMemoryConfig.setSecret(secret);

        tcAuthMemoryConfig.setAccessTokenUrl(accessTokenUrl);
        tcAuthMemoryConfig.setApiSignTicketUrl(bibleItemService.get(TcBibleConstants.BIBLE_CODE, TcBibleConstants.API_SIGN_TICKET_URL));
        tcAuthMemoryConfig.setApiNonceTicketUrl(bibleItemService.get(TcBibleConstants.BIBLE_CODE, TcBibleConstants.API_NONCE_TICKET_URL));

        TcAccessToken accessToken = new TcAccessToken();
        accessToken.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setAccessToken(accessToken);

        TcApiSignTicket apiSignTicket = new TcApiSignTicket();
        apiSignTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiSignTicket(apiSignTicket);

        TcApiNonceTicket apiNonceTicket = new TcApiNonceTicket();
        apiNonceTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiNonceTicket(apiNonceTicket);

        return tcAuthMemoryConfig;
    }

    @Bean
//    @ConditionalOnMissingBean
    public TcService tcService(TcConfig tcConfig){
        TcService tcService = new TcServiceImpl();
        tcService.setTcAuthConfig(tcConfig);
        return tcService;
    }

}
