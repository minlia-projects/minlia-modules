package com.minlia.module.wechat.ma.mapper;

import com.minlia.module.wechat.ma.bean.qo.WechatOpenAccountQO;
import com.minlia.module.wechat.ma.bean.domain.WechatOpenAccount;

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

    WechatOpenAccount queryOne(WechatOpenAccountQO qo);

    List<WechatOpenAccount> queryList(WechatOpenAccountQO qo);

}
