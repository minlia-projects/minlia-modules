//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.qcloud.start.config;

import com.minlia.module.bible.service.BibleItemService;
import com.minlia.modules.qcloud.start.bean.QcloudAccessToken;
import com.minlia.modules.qcloud.start.bean.QcloudApiNonceTicket;
import com.minlia.modules.qcloud.start.bean.QcloudApiSignTicket;
import com.minlia.modules.qcloud.start.constant.QcloudBibleConstants;
import com.minlia.modules.qcloud.start.service.QcloudAuthService;
import com.minlia.modules.qcloud.start.service.QcloudAuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by garen on 2018/4/19.
 */
@Configuration
@ConditionalOnClass(QcloudAuthService.class)
public class QcloudAutoConfiguration {

    @Autowired
    private BibleItemService bibleItemService;

    @Bean
//    @ConditionalOnMissingBean
    public QcloudConfig qcloudConfig() {
        String appid = bibleItemService.get(QcloudBibleConstants.BIBLE_CODE, QcloudBibleConstants.APPID);
        String secret = bibleItemService.get(QcloudBibleConstants.BIBLE_CODE, QcloudBibleConstants.SECRET);

        String accessTokenUrl = bibleItemService.get(QcloudBibleConstants.BIBLE_CODE, QcloudBibleConstants.ACCESS_TOKEN_URL);
        accessTokenUrl = String.format(accessTokenUrl,appid,secret);
        accessTokenUrl = accessTokenUrl.replace("\n","");

        QcloudConfigMemoryConfig tcAuthMemoryConfig = new QcloudConfigMemoryConfig();
        tcAuthMemoryConfig.setAppid(appid);
        tcAuthMemoryConfig.setSecret(secret);

        tcAuthMemoryConfig.setAccessTokenUrl(accessTokenUrl);
        tcAuthMemoryConfig.setApiSignTicketUrl(bibleItemService.get(QcloudBibleConstants.BIBLE_CODE, QcloudBibleConstants.API_SIGN_TICKET_URL));
        tcAuthMemoryConfig.setApiNonceTicketUrl(bibleItemService.get(QcloudBibleConstants.BIBLE_CODE, QcloudBibleConstants.API_NONCE_TICKET_URL));

        QcloudAccessToken accessToken = new QcloudAccessToken();
        accessToken.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setAccessToken(accessToken);

        QcloudApiSignTicket apiSignTicket = new QcloudApiSignTicket();
        apiSignTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiSignTicket(apiSignTicket);

        QcloudApiNonceTicket apiNonceTicket = new QcloudApiNonceTicket();
        apiNonceTicket.setExpireTime(System.currentTimeMillis());
        tcAuthMemoryConfig.setApiNonceTicket(apiNonceTicket);

        return tcAuthMemoryConfig;
    }

    @Bean
//    @ConditionalOnMissingBean
    public QcloudAuthService qcloudAuthService(QcloudConfig qcloudConfig){
        QcloudAuthService qcloudAuthService = new QcloudAuthServiceImpl();
        qcloudAuthService.setTcAuthConfig(qcloudConfig);
        return qcloudAuthService;
    }

}
