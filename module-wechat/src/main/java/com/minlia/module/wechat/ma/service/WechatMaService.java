package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.wechat.ma.bean.ro.WechatMaEncryptedDataRO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaQrcodeRO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaUserDetailRO;
import com.minlia.modules.aliyun.oss.bean.OssFile;

/**
 * Created by will on 6/26/17.
 */
public interface WechatMaService {

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
    WxMaJscode2SessionResult getSessionInfo(String code);

    /**
     * 获取小程序Session信息
     * @param wxMaType
     * @param code
     * @return
     */
    WxMaJscode2SessionResult getSessionInfo(String wxMaType, String code);

    /**
     * 获取小程序Session信息
     * @param wxMaService
     * @param code
     * @return
     */
    WxMaJscode2SessionResult getSessionInfo(WxMaService wxMaService, String code);

    /**
     * 获取绑定手机号码信息
     * @param body
     * @return
     */
    WxMaPhoneNumberInfo getBoundPhoneNumber(WechatMaEncryptedDataRO body);


    WxMaUserInfo decrypt(WechatMaUserDetailRO to);

    WxMaUserInfo decrypt(WxMaService wxMaService, String sessionKey, String EncryptedData, String iv);

    /**
     * 创建二维码
     * @param body
     * @return
     * @throws Exception
     */
    OssFile createWxCodeLimit(WechatMaQrcodeRO body);

    OssFile createWxaCodeUnlimit(String scene, String path, String businessType, String businessId);

}
