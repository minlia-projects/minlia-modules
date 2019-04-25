package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.bean.entity.WechatMaUser;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;

import java.util.List;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatMaUserMapper {

    int create(WechatMaUser wechatMaUser);

    int update(WechatMaUser wechatMaUser);

    int delete(Long id);

    long queryCount(WechatMaUserQO qo);

    WechatMaUser queryOne(WechatMaUserQO qo);

    List<WechatMaUser> queryList(WechatMaUserQO qo);

}
