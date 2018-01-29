package com.minlia.module.wechat.miniapp.mapper;

import com.minlia.module.wechat.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.miniapp.entity.WechatOpenAccount;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 6/25/17.
 */
@Component
public interface WechatOpenAccountMapper{

    void create(WechatOpenAccount openAccount);

    void update(WechatOpenAccount openAccount);

//    @Query(nativeQuery = true,value = "UPDATE MDL_WechatOpenAccount SET userId = ?1 WHERE unionId = ?2}")
    void updateUserByUnionId(Long userId,String unionId);

    long countByUnionIdAndUserIdIsNotNull(String unionId);

    List<WechatOpenAccount> findByUnionIdAndUserIdIsNotNull(String unionId);

    WechatOpenAccount queryOne(WechatOpenAccountQueryBody body);

    List<WechatOpenAccount> queryList(WechatOpenAccountQueryBody body);

}
