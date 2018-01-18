package com.minlia.module.tencent.miniapp.service;

import com.minlia.module.tencent.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.tencent.miniapp.domain.WechatOpenAccount;

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

    WechatOpenAccount findOne(WechatOpenAccountQueryBody body);

    List<WechatOpenAccount> findList(WechatOpenAccountQueryBody body);

}
