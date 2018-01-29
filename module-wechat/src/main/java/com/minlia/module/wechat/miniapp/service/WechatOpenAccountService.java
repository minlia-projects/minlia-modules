package com.minlia.module.wechat.miniapp.service;

import com.minlia.module.wechat.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.miniapp.entity.WechatOpenAccount;

import java.util.List;

/**
 * Created by will on 6/25/17.
 */
public interface WechatOpenAccountService {

    WechatOpenAccount create(WechatOpenAccount openAccount);

    WechatOpenAccount update(WechatOpenAccount openAccount);

    void updateUserByUnionId(Long userId,String unionId);

    long countByUnionIdAndUserIdIsNotNull(String unionId);

    List<WechatOpenAccount> findByUnionIdAndUserIdIsNotNull(String unionId);

    WechatOpenAccount queryOne(WechatOpenAccountQueryBody body);

    List<WechatOpenAccount> queryList(WechatOpenAccountQueryBody body);

}
