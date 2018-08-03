package com.minlia.module.wechat.ma.mapper;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.module.wechat.ma.entity.WechatMaUser;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WxMaUserMapper {

    void create(WxMaUserInfo wxMaUserInfo);

    void update(WxMaUserInfo wxMaUserInfo);

    WechatMaUser queryByGuid(String guid);

}
