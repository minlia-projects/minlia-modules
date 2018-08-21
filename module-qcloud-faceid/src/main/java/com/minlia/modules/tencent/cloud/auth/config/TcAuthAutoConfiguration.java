//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.tencent.cloud.auth.config;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.modules.tencent.cloud.auth.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiNonceTicket;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiSignTicket;
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
        String appid = bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE, TcAuthBibleConstants.APPID);
        String secret = bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE, TcAuthBibleConstants.SECRET);

        String accessTokenUrl = bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE,TcAuthBibleConstants.ACCESS_TOKEN_URL);
        accessTokenUrl = String.format(accessTokenUrl,appid,secret);
        accessTokenUrl = accessTokenUrl.replace("\n","");

        TcAuthMemoryConfig tcAuthMemoryConfig = new TcAuthMemoryConfig();
        tcAuthMemoryConfig.setAppid(appid);
        tcAuthMemoryConfig.setSecret(secret);

        tcAuthMemoryConfig.setAccessTokenUrl(accessTokenUrl);
        tcAuthMemoryConfig.setApiSignTicketUrl(bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE,TcAuthBibleConstants.API_SIGN_TICKET_URL));
        tcAuthMemoryConfig.setApiNonceTicketUrl(bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE,TcAuthBibleConstants.API_NONCE_TICKET_URL));

        TcAccessToken accessToken = new TcAccessToken();
        accessToken.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setAccessToken(accessToken);

        TcApiSignTicket apiSignTicket = new TcApiSignTicket();
        apiSignTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiSignTicket(apiSignTicket);

        TcApiNonceTicket apiNonceTicket = new TcApiNonceTicket();
        apiNonceTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiNonceTicket(apiNonceTicket);


        tcAuthMemoryConfig.setH5faceidResultUrl(bibleItemService.get(TcAuthBibleConstants.BIBLE_CODE,TcAuthBibleConstants.H5FACEID_RESULT_URL));

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
