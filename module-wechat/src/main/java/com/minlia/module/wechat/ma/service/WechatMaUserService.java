package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.entity.WechatMaUser;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatMaUserService {

    WechatMaUser update(MiniappUserDetailRequestBody body);

    WechatMaUser me();

    WxMaUserInfo decrypt(MiniappUserDetailRequestBody body);

}
