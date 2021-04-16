package com.minlia.module.wechat.miniapp.service;


import com.minlia.cloud.body.Response;
import com.minlia.module.wechat.miniapp.bean.WechatMaLoginRo;


/**
 * @author garen
 */
public interface WechatAuthService {

    Response login(WechatMaLoginRo ro);

}