package com.minlia.modules.tencent.cloud.start.service;

import com.minlia.modules.tencent.cloud.start.config.TcConfig;

/**
 * Created by garen on 2018/4/19.
 */
public interface TcService {

    String getAccessToken();

    String getAccessToken(boolean forceRefresh);

    String getApiSignTicket();

    String getApiSignTicket(boolean forceRefresh);

    String getApiNonceTicket();

    String getApiNonceTicket(boolean forceRefresh);

    TcConfig getAuthConfig();

    TcConfig setTcAuthConfig(TcConfig tcConfig);

}
