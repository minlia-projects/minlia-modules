package com.minlia.module.wechat.miniapp.mapper;

import com.minlia.module.wechat.miniapp.entity.WechatUserDetail;
import org.springframework.stereotype.Component;

/**
 * Created by will on 6/25/17.
 */
@Component
public interface WechatUserDetailMapper {

    void create(WechatUserDetail wechatUserDetail);

    void update(WechatUserDetail wechatUserDetail);

    WechatUserDetail queryByGuid(String guid);
}
