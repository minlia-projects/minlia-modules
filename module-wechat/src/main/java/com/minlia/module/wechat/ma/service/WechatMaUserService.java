package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.wechat.ma.bean.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.entity.WechatMaUser;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatMaUserService {

    WechatMaUser update(WechatMaUser wechatMaUser);

    WechatMaUser update(MiniappUserDetailRequestBody body);

    WechatMaUser update(WxMaUserInfo wxMaUserInfo, String code, String guid);

    WechatMaUser me();

    WechatMaUser queryOne(WechatMaUserQO qo);

    @Deprecated
    WxMaUserInfo decrypt(MiniappUserDetailRequestBody body);

}
