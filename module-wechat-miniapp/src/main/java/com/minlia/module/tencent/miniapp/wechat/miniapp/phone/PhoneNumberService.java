package com.minlia.module.tencent.miniapp.wechat.miniapp.phone;

/**
 * Created by will on 9/12/17.
 * 从小程序里获取到当前微信用户绑定的手机号码
 */
public interface PhoneNumberService {

  PhoneNumberResponseBody getBoundPhoneNumber(PhoneNumberRequestBody body);

}
