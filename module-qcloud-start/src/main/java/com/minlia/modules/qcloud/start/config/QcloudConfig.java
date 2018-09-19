package com.minlia.modules.qcloud.start.config;

import com.minlia.modules.qcloud.start.bean.QcloudAccessToken;
import com.minlia.modules.qcloud.start.bean.QcloudApiSignTicket;
import com.minlia.modules.qcloud.start.bean.QcloudApiNonceTicket;

import java.util.concurrent.locks.Lock;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudConfig {

    String getAppid();

    String getSecret();

    Lock getTcLock();

    /**
     * Access Token
     * @return
     */
    QcloudAccessToken getAccessToken();

    String getAccessTokenUrl();

    boolean isAccessTokenExpired();

    void expireAccessToken();

    void updateAccessToken(QcloudAccessToken tcAccessToken);

    void updateAccessToken(String value, int expireIn);

    /**
     * Api Sign Ticket
     * @return
     */
    QcloudApiSignTicket getApiSignTicket();

    String getApiSignTicketUrl();

    boolean isApiSignTicketExpired();

    void expireApiSignTicket();

    void updateApiSignTicket(QcloudApiSignTicket apiSignTicket);

    void updateApiSignTicket(String value, int expireIn);

    /**
     * Api Nonce Ticket
     * @return
     */
    QcloudApiNonceTicket getApiNonceTicket();

    String getApiNonceTicketUrl();

    boolean isApiNonceTicketExpired();

    void expireApiNonceTicket();

    void updateApiNonceTicket(QcloudApiNonceTicket apiNonceTicket);

    void updateApiNonceTicket(String value, int expireIn);

}
