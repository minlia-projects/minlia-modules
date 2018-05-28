package com.minlia.module.wechat.ma.service;

import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.entity.WechatUser;
import me.chanjar.weixin.common.exception.WxErrorException;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatUserService {

    WechatUser updateUserDetail(MiniappUserDetailRequestBody body);

    WechatUser updateByOpenId(String guid, String openId);

    WechatUser showUserDetail();

}
