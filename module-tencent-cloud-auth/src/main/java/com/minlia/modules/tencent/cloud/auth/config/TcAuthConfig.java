//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.tencent.cloud.auth.config;

import com.minlia.modules.tencent.cloud.auth.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiTicket;

import java.util.concurrent.locks.Lock;

/**
 * Created by garen on 2018/4/19.
 */
public interface TcAuthConfig {

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
     * Api Ticket
     * @return
     */
    TcApiTicket getApiTicket();

    String getApiTicketUrl();

    boolean isApiTicketExpired();

    void expireApiTicket();

    void updateApiTicket(TcApiTicket tcApiTicket);

    void updateApiTicket(String value, int expireIn);

}
