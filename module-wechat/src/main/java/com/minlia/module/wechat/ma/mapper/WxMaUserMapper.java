package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.entity.WechatMaUser;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WxMaUserMapper {

    void create(WechatMaUser wechatUserDetail);

    void update(WechatMaUser wechatUserDetail);

    WechatMaUser queryByGuid(String guid);

}
