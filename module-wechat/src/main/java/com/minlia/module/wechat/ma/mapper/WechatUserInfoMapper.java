package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.entity.WechatUserInfo;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatUserInfoMapper {

    void create(WechatUserInfo wechatUserDetail);

    void update(WechatUserInfo wechatUserDetail);

    WechatUserInfo queryByGuid(String guid);

}
