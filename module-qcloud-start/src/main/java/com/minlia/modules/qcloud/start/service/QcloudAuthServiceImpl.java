package com.minlia.modules.qcloud.start.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.qcloud.start.body.QcloudAccessTokenResponseBody;
import com.minlia.modules.qcloud.start.body.QcloudApiTicketResponseBody;
import com.minlia.modules.qcloud.start.body.QcloudApiTickets;
import com.minlia.modules.qcloud.start.config.QcloudConfig;
import com.minlia.modules.qcloud.start.constant.QcloudCode;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.locks.Lock;

/**
 * Created by garen on 2018/4/19.
 */
@Slf4j
@Service
public class QcloudAuthServiceImpl implements QcloudAuthService {

    private QcloudConfig qcloudConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getAccessToken() {
        return this.getAccessToken(false);
    }

    @Override
    public String getAccessToken(boolean forceRefresh) {
        Lock lock = this.qcloudConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.qcloudConfig.expireAccessToken();
            }
            if(this.qcloudConfig.isAccessTokenExpired()) {
                //如果失效重新获取
                String url = this.qcloudConfig.getAccessTokenUrl();
                QcloudAccessTokenResponseBody responseBody = restTemplate.getForObject(url, QcloudAccessTokenResponseBody.class);
                if (responseBody.isSuccess()) {
                    this.qcloudConfig.updateAccessToken(responseBody.getAccessToken(),responseBody.getExpireIn());
                } else {
                    ApiAssert.state(false, QcloudCode.Message.GET_ACCESS_TOKEN_FAILURE, responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.qcloudConfig.getAccessToken().getValue();
    }

    @Override
    public String getApiSignTicket() {
        return this.getApiSignTicket(false);
    }

    @Override
    public String getApiSignTicket(boolean forceRefresh) {
        Lock lock = this.qcloudConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.qcloudConfig.expireApiSignTicket();
            }
            if(this.qcloudConfig.isApiSignTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.qcloudConfig.getApiSignTicketUrl(), qcloudConfig.getAppid(), this.getAccessToken());
                QcloudApiTicketResponseBody responseBody = restTemplate.getForObject(url, QcloudApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    QcloudApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.qcloudConfig.updateApiSignTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiAssert.state(false, QcloudCode.Message.GET_API_SIGN_TICKET_FAILURE, responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.qcloudConfig.getApiSignTicket().getValue();
    }

    @Override
    public String getApiNonceTicket() {
        return getApiNonceTicket(false);
    }

    @Override
    public String getApiNonceTicket(boolean forceRefresh) {
        Lock lock = this.qcloudConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.qcloudConfig.expireApiNonceTicket();
            }
            if(this.qcloudConfig.isApiNonceTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.qcloudConfig.getApiNonceTicketUrl(), qcloudConfig.getAppid(), this.getAccessToken(), SecurityContextHolder.getCurrentGuid());
                QcloudApiTicketResponseBody responseBody = restTemplate.getForObject(url, QcloudApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    QcloudApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.qcloudConfig.updateApiNonceTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiAssert.state(false, QcloudCode.Message.GET_API_NONCE_TICKET_FAILURE, responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.qcloudConfig.getApiNonceTicket().getValue();
    }

    @Override
    public QcloudConfig getAuthConfig() {
        return this.qcloudConfig;
    }

    @Override
    public QcloudConfig setTcAuthConfig(QcloudConfig qcloudConfig) {
        return this.qcloudConfig = qcloudConfig;
    }

}
