package com.minlia.module.tencent.miniapp.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.module.tencent.miniapp.body.MiniappUserDetailRequestBody;
import com.minlia.module.tencent.miniapp.domain.WechatUserDetail;

/**
 * Created by will on 6/25/17.
 */
public interface WechatUserDetailService {

    StatefulBody updateUserDetail(MiniappUserDetailRequestBody body);

    WechatUserDetail showUserDetail();

}
