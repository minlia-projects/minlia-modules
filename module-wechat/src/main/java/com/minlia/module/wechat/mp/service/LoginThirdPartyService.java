package com.minlia.module.wechat.mp.service;


import com.minlia.cloud.body.Response;
import com.minlia.module.wechat.mp.body.BindWxRequestBody;
import com.minlia.module.wechat.mp.body.LoginWechatRequestBody;
import me.chanjar.weixin.common.error.WxErrorException;


public interface LoginThirdPartyService {

    Response loginByWxMpCode(LoginWechatRequestBody body) throws WxErrorException;

    Response loginByWxMaCode(LoginWechatRequestBody body);

    Response bindByWxma(BindWxRequestBody body);

}
