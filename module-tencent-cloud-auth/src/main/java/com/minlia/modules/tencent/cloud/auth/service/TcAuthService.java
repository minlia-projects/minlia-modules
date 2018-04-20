package com.minlia.modules.tencent.cloud.auth.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.tencent.cloud.auth.body.TcFaceIdRequestBody;
import com.minlia.modules.tencent.cloud.auth.config.TcAuthConfig;

/**
 * Created by garen on 2018/4/19.
 */
public interface TcAuthService {

    String getAccessToken();

    String getAccessToken(boolean forceRefresh);

    String getApiSignTicket();

    String getApiSignTicket(boolean forceRefresh);

    String getApiNonceTicket();

    String getApiNonceTicket(boolean forceRefresh);

    TcAuthConfig getAuthConfig();

    TcAuthConfig setTcAuthConfig(TcAuthConfig tcAuthConfig);

    String sign(String orderNo,String name,String idNo,String userId);

    StatefulBody geth5faceid(TcFaceIdRequestBody requestBody);

}
