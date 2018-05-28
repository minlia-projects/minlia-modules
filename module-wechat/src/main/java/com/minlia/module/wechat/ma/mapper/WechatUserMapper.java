package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.entity.WechatUser;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatUserMapper {

    void create(WechatUser wechatUserDetail);

    void update(WechatUser wechatUserDetail);

    WechatUser queryByGuid(String guid);

}
