package com.minlia.module.wechat.login.service;

import com.minlia.module.wechat.login.ro.WechatUserQO;
import com.minlia.module.wechat.login.entity.WechatUser;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;

/**
 *
 * @author will
 * @date 2017/6/25
 */
public interface WechatUserService {

    WechatUser create(WechatUser openAccount);

    /**
     * 根据微信消息保存union、opoen信息
     * @param wxMpUser
     */
    void save(WxMpUser wxMpUser);

    WechatUser update(WechatUser openAccount);

    void updateGuidByUnionId(String guid,String unionId);

    long countByUnionIdAndGuidNotNull(String unionId);

    List<WechatUser> queryByUnionIdAndGuidNotNull(String unionId);

    WechatUser queryOne(WechatUserQO body);

    List<WechatUser> queryList(WechatUserQO body);

}
