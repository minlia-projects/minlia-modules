package com.minlia.modules.tencent.cloud.start.config;

import com.minlia.modules.tencent.cloud.start.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.start.bean.TcApiNonceTicket;
import com.minlia.modules.tencent.cloud.start.bean.TcApiSignTicket;

import java.util.concurrent.locks.Lock;

/**
 * Created by garen on 2018/4/19.
 */
public interface TcConfig {

    String getAppid();

    String getSecret();

    Lock getTcLock();

    /**
     * Access Token
     * @return
     */
    TcAccessToken getAccessToken();

    String getAccessTokenUrl();

    boolean isAccessTokenExpired();

    void expireAccessToken();

    void updateAccessToken(TcAccessToken tcAccessToken);

    void updateAccessToken(String value, int expireIn);

    /**
     * Api Sign Ticket
     * @return
     */
    TcApiSignTicket getApiSignTicket();

    String getApiSignTicketUrl();

    boolean isApiSignTicketExpired();

    void expireApiSignTicket();

    void updateApiSignTicket(TcApiSignTicket apiSignTicket);

    void updateApiSignTicket(String value, int expireIn);

    /**
     * Api Nonce Ticket
     * @return
     */
    TcApiNonceTicket getApiNonceTicket();

    String getApiNonceTicketUrl();

    boolean isApiNonceTicketExpired();

    void expireApiNonceTicket();

    void updateApiNonceTicket(TcApiNonceTicket apiNonceTicket);

    void updateApiNonceTicket(String value, int expireIn);

}
