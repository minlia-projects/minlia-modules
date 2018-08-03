package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatMaUserService {

    WxMaUserInfo update(MiniappUserDetailRequestBody body);

    WxMaUserInfo me();

}
