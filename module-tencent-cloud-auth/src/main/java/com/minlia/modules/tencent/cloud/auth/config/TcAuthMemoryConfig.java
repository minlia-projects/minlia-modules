//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.modules.tencent.cloud.auth.config;

import com.minlia.modules.tencent.cloud.auth.bean.TcAccessToken;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiNonceTicket;
import com.minlia.modules.tencent.cloud.auth.bean.TcApiSignTicket;
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

    protected volatile TcApiSignTicket apiSignTicket;
    protected volatile String apiSignTicketUrl;

    protected volatile TcApiNonceTicket apiNonceTicket;
    protected volatile String apiNonceTicketUrl;


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
    public TcApiSignTicket getApiSignTicket() {
        return this.apiSignTicket;
    }

    public void setApiSignTicket(TcApiSignTicket apiSignTicket) {
        this.apiSignTicket = apiSignTicket;
    }

    @Override
    public String getApiSignTicketUrl() {
        return apiSignTicketUrl;
    }

    public void setApiSignTicketUrl(String apiSignTicketUrl) {
        this.apiSignTicketUrl = apiSignTicketUrl;
    }

    @Override
    public boolean isApiSignTicketExpired() {
        return System.currentTimeMillis() > this.apiSignTicket.getExpireTime();
    }

    @Override
    public void expireApiSignTicket() {
        this.apiSignTicket.setExpireTime(0L);
    }

    @Override
    public void updateApiSignTicket(TcApiSignTicket apiTicket) {
        this.updateApiSignTicket(apiTicket.getValue(),apiTicket.getExpireIn());
    }

    @Override
    public void updateApiSignTicket(String value, int expireIn) {
        this.apiSignTicket.setValue(value);
        this.apiSignTicket.setExpireTime(System.currentTimeMillis() + (long)(expireIn - 600)*1000);
    }



    @Override
    public TcApiNonceTicket getApiNonceTicket() {
        return this.apiNonceTicket;
    }

    public void setApiNonceTicket(TcApiNonceTicket apiNonceTicket) {
        this.apiNonceTicket = apiNonceTicket;
    }

    @Override
    public String getApiNonceTicketUrl() {
        return apiNonceTicketUrl;
    }

    public void setApiNonceTicketUrl(String apiNonceTicketUrl) {
        this.apiNonceTicketUrl = apiNonceTicketUrl;
    }

    @Override
    public boolean isApiNonceTicketExpired() {
        return System.currentTimeMillis() > this.apiNonceTicket.getExpireTime();
    }

    @Override
    public void expireApiNonceTicket() {
        this.apiNonceTicket.setExpireTime(0L);
    }

    @Override
    public void updateApiNonceTicket(TcApiNonceTicket apiTicket) {
        this.updateApiNonceTicket(apiTicket.getValue(),apiTicket.getExpireIn());
    }

    @Override
    public void updateApiNonceTicket(String value, int expireIn) {
        this.apiNonceTicket.setValue(value);
        this.apiNonceTicket.setExpireTime(System.currentTimeMillis() + (long)(expireIn - 20)*1000);
    }


    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
