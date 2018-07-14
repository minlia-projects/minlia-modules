package com.minlia.module.wechat.ma.service;

import com.minlia.module.wechat.ma.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

import java.util.List;

/**
 *
 * @author will
 * @date 2017/6/25
 */
public interface WechatOpenAccountService {

    WechatOpenAccount create(WechatOpenAccount openAccount);

    /**
     * 根据微信消息保存union、opoen信息
     * @param wxMpUser
     */
    void save(WxMpUser wxMpUser);

    WechatOpenAccount update(WechatOpenAccount openAccount);

    void updateGuidByUnionId(String guid,String unionId);

    long countByUnionIdAndGuidNotNull(String unionId);

    List<WechatOpenAccount> queryByUnionIdAndGuidNotNull(String unionId);

    WechatOpenAccount queryOne(WechatOpenAccountQueryBody body);

    List<WechatOpenAccount> queryList(WechatOpenAccountQueryBody body);

}
