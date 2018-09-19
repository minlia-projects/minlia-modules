package com.minlia.modules.qcloud.start.service;

import com.minlia.modules.qcloud.start.config.QcloudConfig;

/**
 * Created by garen on 2018/4/19.
 */
public interface QcloudAuthService {

    String getAccessToken();

    String getAccessToken(boolean forceRefresh);

    String getApiSignTicket();

    String getApiSignTicket(boolean forceRefresh);

    String getApiNonceTicket();

    String getApiNonceTicket(boolean forceRefresh);

    QcloudConfig getAuthConfig();

    QcloudConfig setTcAuthConfig(QcloudConfig qcloudConfig);

}
