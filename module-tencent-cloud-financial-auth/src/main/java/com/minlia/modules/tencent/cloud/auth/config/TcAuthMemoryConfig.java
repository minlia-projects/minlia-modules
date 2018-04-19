//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.tencent.cloud.auth.config;

import com.minlia.modules.tencent.cloud.auth.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiTicket;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.stereotype.Service;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *  Created by garen on 2018/4/19.
 */
@Service
public class TcAuthMemoryConfig implements TcAuthConfig {

    public TcAuthMemoryConfig() {}

    protected volatile String appid;
    protected volatile String secret;
    protected Lock tcLock = new ReentrantLock();

    protected volatile TcAccessToken accessToken;
    protected volatile String accessTokenUrl;

    protected volatile TcApiTicket apiTicket;
    protected volatile String apiTicketUrl;

    @Override
    public String getAppid() {
        return this.appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Override
    public String getSecret() {
        return this.secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    @Override
    public Lock getTcLock() {
        return tcLock;
    }

    public void setTcLock(Lock tcLock) {
        this.tcLock = tcLock;
    }



    @Override
    public TcAccessToken getAccessToken() {
        return this.accessToken;
    }

    public void setAccessToken(TcAccessToken accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getAccessTokenUrl() {
        return this.accessTokenUrl;
    }

    public void setAccessTokenUrl(String accessTokenUrl) {
        this.accessTokenUrl = accessTokenUrl;
    }

    @Override
    public boolean isAccessTokenExpired() {
        return System.currentTimeMillis() > this.accessToken.getExpireTime();
    }

    @Override
    public void expireAccessToken() {
        this.accessToken.setExpireTime(0L);
    }

    @Override
    public synchronized void updateAccessToken(TcAccessToken accessToken) {
        this.updateAccessToken(accessToken.getValue(), accessToken.getExpireIn());
    }

    @Override
    public synchronized void updateAccessToken(String value, int expiresInSeconds) {
        this.accessToken.setValue(value);
        this.accessToken.setExpireTime(System.currentTimeMillis() + (long)(expiresInSeconds - 600)*1000);
    }



    @Override
    public TcApiTicket getApiTicket() {
        return this.apiTicket;
    }

    public void setApiTicket(TcApiTicket apiTicket) {
        this.apiTicket = apiTicket;
    }

    @Override
    public String getApiTicketUrl() {
        return apiTicketUrl;
    }

    public void setApiTicketUrl(String apiTicketUrl) {
        this.apiTicketUrl = apiTicketUrl;
    }

    @Override
    public boolean isApiTicketExpired() {
        return System.currentTimeMillis() > this.apiTicket.getExpireTime();
    }

    @Override
    public void expireApiTicket() {
        this.apiTicket.setExpireTime(0L);
    }

    @Override
    public void updateApiTicket(TcApiTicket apiTicket) {
        this.updateApiTicket(apiTicket.getValue(),apiTicket.getExpireIn());
    }

    @Override
    public void updateApiTicket(String value, int expireIn) {
        this.apiTicket.setValue(value);
        this.apiTicket.setExpireTime(System.currentTimeMillis() + (long)(expireIn - 600)*1000);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
