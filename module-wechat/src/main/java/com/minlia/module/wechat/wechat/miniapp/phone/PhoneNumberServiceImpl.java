package com.minlia.module.wechat.wechat.miniapp.phone;

import com.minlia.module.wechat.ma.body.WechatSession;
import com.minlia.module.wechat.ma.service.WechatMiniappService;
import com.minlia.module.wechat.wechat.miniapp.WechatSessionRemoteApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by will on 9/12/17.
 */
@Service
@Slf4j
public class PhoneNumberServiceImpl implements PhoneNumberService {

  @Autowired
  private WechatMiniappService wechatMiniappService;
  @Autowired
  private WechatSessionRemoteApi wechatSessionRemoteApi;

  @Override
  public PhoneNumberResponseBody getBoundPhoneNumber(PhoneNumberRequestBody body) {
    WechatSession session = wechatMiniappService.getSessionInfo(body.getCode());
    PhoneNumberResponseBody data = (PhoneNumberResponseBody) wechatSessionRemoteApi.decryptData(body.getEncryptedData(), body.getIv(), session.getSessionKey(),PhoneNumberResponseBody.class);
    return data;
  }
}
