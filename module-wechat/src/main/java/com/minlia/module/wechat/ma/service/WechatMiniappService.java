package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import com.minlia.module.wechat.ma.body.MiniappQrcodeRequestBody;
import com.minlia.module.wechat.ma.body.WechatSession;
import com.minlia.modules.aliyun.oss.bean.OssFile;

/**
 * Created by will on 6/26/17.
 */
public interface WechatMiniappService {

    /**
     * 通过bible配置的code
     * @param type
     * @return
     */
    WxMaService getWxMaService(String type);

    /**
     * 获取小程序Session信息
     * @param code
     * @return
     */
    WechatSession getSessionInfo(String code);

    /**
     * 获取小程序Session信息
     * @param wxMaType
     * @param code
     * @return
     */
    WechatSession getSessionInfo(String wxMaType, String code);

    /**
     * 获取小程序Session信息
     * @param wxMaService
     * @param code
     * @return
     */
    WechatSession getSessionInfo(WxMaService wxMaService, String code);

    /**
     * 创建二维码
     * @param body
     * @return
     * @throws Exception
     */
    OssFile createWxCodeLimit(MiniappQrcodeRequestBody body);

}
