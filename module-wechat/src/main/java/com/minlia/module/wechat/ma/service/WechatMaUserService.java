package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.wechat.ma.bean.entity.WechatMaUser;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaUserDetailRO;

import java.util.List;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatMaUserService {

    WechatMaUser update(WechatMaUser wechatMaUser);

    WechatMaUser update(WechatMaUserDetailRO to);

    WechatMaUser update(WxMaUserInfo wxMaUserInfo, String code, String guid);

    WechatMaUser me();

    WechatMaUser queryOne(WechatMaUserQO qo);

    List<WechatMaUser> queryList(WechatMaUserQO qo);

}
