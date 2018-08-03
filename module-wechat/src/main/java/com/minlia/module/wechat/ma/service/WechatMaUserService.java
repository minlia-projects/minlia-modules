package com.minlia.module.wechat.ma.service;

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

}
