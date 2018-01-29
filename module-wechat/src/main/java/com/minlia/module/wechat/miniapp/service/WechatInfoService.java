package com.minlia.module.wechat.miniapp.service;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * Created by will on 7/25/17.
 * 模板消息通知服务
 */
public interface WechatInfoService {

    /**
     * 根据微信消息保存union、opoen信息
     * @param wxMpUser
     */
    void save(WxMpUser wxMpUser) throws WxErrorException;

}
