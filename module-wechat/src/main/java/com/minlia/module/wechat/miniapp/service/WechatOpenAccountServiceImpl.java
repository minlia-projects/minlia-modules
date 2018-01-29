package com.minlia.module.wechat.miniapp.service;

import com.minlia.module.wechat.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.miniapp.entity.WechatOpenAccount;
import com.minlia.module.wechat.miniapp.mapper.WechatOpenAccountMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WechatOpenAccountServiceImpl implements WechatOpenAccountService {

    @Autowired
    private WechatOpenAccountMapper wechatOpenAccountMapper;

    @Override
    public WechatOpenAccount create(WechatOpenAccount openAccount) {
        wechatOpenAccountMapper.create(openAccount);
        return openAccount;
    }

    @Override
    public WechatOpenAccount update(WechatOpenAccount openAccount) {
        wechatOpenAccountMapper.update(openAccount);
        return openAccount;
    }

    @Override
    public void updateUserByUnionId(Long userId, String unionId) {
        wechatOpenAccountMapper.updateUserByUnionId(userId,unionId);
    }

    @Override
    public long countByUnionIdAndUserIdIsNotNull(String unionId) {
        return wechatOpenAccountMapper.countByUnionIdAndUserIdIsNotNull(unionId);
    }

    @Override
    public List<WechatOpenAccount> findByUnionIdAndUserIdIsNotNull(String unionId) {
        return wechatOpenAccountMapper.findByUnionIdAndUserIdIsNotNull(unionId);
    }

    @Override
    public WechatOpenAccount queryOne(WechatOpenAccountQueryBody body) {
        return wechatOpenAccountMapper.queryOne(body);
    }

    @Override
    public List<WechatOpenAccount> queryList(WechatOpenAccountQueryBody body) {
        return wechatOpenAccountMapper.queryList(body);
    }

}
