package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;

import java.util.List;

/**
 *
 * @author garen
 * @date 2017/6/25
 */
public interface WechatOpenAccountMapper{

    void create(WechatOpenAccount openAccount);

    void update(WechatOpenAccount openAccount);

    void updateGuidByUnionId(String guid,String unionId);

    long countByUnionIdAndGuidNotNull(String unionId);

    List<WechatOpenAccount> queryByUnionIdAndGuidNotNull(String unionId);

    WechatOpenAccount queryOne(WechatOpenAccountQueryBody body);

    List<WechatOpenAccount> queryList(WechatOpenAccountQueryBody body);

}
