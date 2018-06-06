package com.minlia.modules.tencent.cloud.start.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.tencent.cloud.start.body.TcAccessTokenResponseBody;
import com.minlia.modules.tencent.cloud.start.body.TcApiTicketResponseBody;
import com.minlia.modules.tencent.cloud.start.body.TcApiTickets;
import com.minlia.modules.tencent.cloud.start.config.TcConfig;
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
public class TcServiceImpl implements TcService {

    private TcConfig tcConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getAccessToken() {
        return this.getAccessToken(false);
    }

    @Override
    public String getAccessToken(boolean forceRefresh) {
        Lock lock = this.tcConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.tcConfig.expireAccessToken();
            }
            if(this.tcConfig.isAccessTokenExpired()) {
                //如果失效重新获取
                String url = this.tcConfig.getAccessTokenUrl();
                TcAccessTokenResponseBody responseBody = restTemplate.getForObject(url, TcAccessTokenResponseBody.class);
                if (responseBody.isSuccess()) {
                    this.tcConfig.updateAccessToken(responseBody.getAccessToken(),responseBody.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取AccessToken失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.tcConfig.getAccessToken().getValue();
    }

    @Override
    public String getApiSignTicket() {
        return this.getApiSignTicket(false);
    }

    @Override
    public String getApiSignTicket(boolean forceRefresh) {
        Lock lock = this.tcConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.tcConfig.expireApiSignTicket();
            }
            if(this.tcConfig.isApiSignTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.tcConfig.getApiSignTicketUrl(), tcConfig.getAppid(), this.getAccessToken());
                TcApiTicketResponseBody responseBody = restTemplate.getForObject(url, TcApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    TcApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.tcConfig.updateApiSignTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取Api Ticket失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.tcConfig.getApiSignTicket().getValue();
    }

    @Override
    public String getApiNonceTicket() {
        return getApiNonceTicket(false);
    }

    @Override
    public String getApiNonceTicket(boolean forceRefresh) {
        Lock lock = this.tcConfig.getTcLock();
        try {
            lock.lock();
            if(forceRefresh) {
                this.tcConfig.expireApiNonceTicket();
            }
            if(this.tcConfig.isApiNonceTicketExpired()) {
                //如果失效重新获取
                String url = String.format(this.tcConfig.getApiNonceTicketUrl(), tcConfig.getAppid(), this.getAccessToken(), SecurityContextHolder.getCurrentGuid());
                TcApiTicketResponseBody responseBody = restTemplate.getForObject(url, TcApiTicketResponseBody.class);
                if (responseBody.isSuccess()) {
                    TcApiTickets tcApiTickets = responseBody.getTickets().get(0);
                    this.tcConfig.updateApiNonceTicket(tcApiTickets.getValue(),tcApiTickets.getExpireIn());
                } else {
                    ApiPreconditions.is(true, ApiCode.NOT_NULL,"获取Api Ticket失败:"+responseBody.getCode());
                }
            }
        } finally {
            lock.unlock();
        }
        return this.tcConfig.getApiNonceTicket().getValue();
    }

    @Override
    public TcConfig getAuthConfig() {
        return this.tcConfig;
    }

    @Override
    public TcConfig setTcAuthConfig(TcConfig tcConfig) {
        return this.tcConfig = tcConfig;
    }

}
