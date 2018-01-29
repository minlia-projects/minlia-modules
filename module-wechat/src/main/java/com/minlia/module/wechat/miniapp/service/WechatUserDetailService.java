package com.minlia.module.wechat.miniapp.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.wechat.miniapp.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.miniapp.entity.WechatUserDetail;

/**
 * Created by will on 6/25/17.
 */
public interface WechatUserDetailService {

    StatefulBody updateUserDetail(MiniappUserDetailRequestBody body);

    WechatUserDetail showUserDetail();

}
