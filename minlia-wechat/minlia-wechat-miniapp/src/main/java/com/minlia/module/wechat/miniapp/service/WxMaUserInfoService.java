package com.minlia.module.wechat.miniapp.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.wechat.miniapp.entity.WxMaUserInfoEntity;

/**
 * <p>
 * 微信小程序信息 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
public interface WxMaUserInfoService extends IService<WxMaUserInfoEntity> {

    WxMaUserInfoEntity save(WxMaJscode2SessionResult session, WxMaUserInfo userInfo, WxMaPhoneNumberInfo phoneNumberInfo);

    WxMaUserInfoEntity getByOpenId(String openId);
}