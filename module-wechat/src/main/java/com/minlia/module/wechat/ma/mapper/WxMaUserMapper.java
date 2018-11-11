package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.bean.domain.WechatMaUser;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WxMaUserMapper {

    int create(WechatMaUser wechatMaUser);

    int update(WechatMaUser wechatMaUser);

    WechatMaUser queryOne(WechatMaUserQO qo);

}
