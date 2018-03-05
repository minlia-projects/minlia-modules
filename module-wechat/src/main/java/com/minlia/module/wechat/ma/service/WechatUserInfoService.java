package com.minlia.module.wechat.ma.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.entity.WechatUserInfo;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatUserInfoService {

    StatefulBody updateUserDetail(MiniappUserDetailRequestBody body);

    WechatUserInfo showUserDetail();

}
