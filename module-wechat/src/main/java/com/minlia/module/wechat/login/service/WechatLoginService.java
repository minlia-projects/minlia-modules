package com.minlia.module.wechat.login.service;


import com.minlia.cloud.body.Response;
import com.minlia.module.wechat.login.bean.WechatBindRO;
import com.minlia.module.wechat.login.bean.WechatLoginRO;
import me.chanjar.weixin.common.error.WxErrorException;


public interface WechatLoginService {

    Response loginByWxMpCode(WechatLoginRO body) throws WxErrorException;

    Response loginByWxMaCode(WechatLoginRO body);

    Response bindByWxma(WechatBindRO body);

}
