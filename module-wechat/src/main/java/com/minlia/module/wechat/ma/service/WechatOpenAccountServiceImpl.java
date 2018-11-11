package com.minlia.module.wechat.ma.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wechat.ma.bean.qo.WechatOpenAccountQO;
import com.minlia.module.wechat.ma.bean.domain.WechatOpenAccount;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
import com.minlia.module.wechat.ma.mapper.WechatOpenAccountMapper;
import com.minlia.module.wechat.mp.constant.WechatMpCode;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class WechatOpenAccountServiceImpl implements WechatOpenAccountService {

    @Autowired
    private WechatOpenAccountMapper wechatOpenAccountMapper;

    @Override
    @Transactional
    public WechatOpenAccount create(WechatOpenAccount openAccount) {
        wechatOpenAccountMapper.create(openAccount);
        return openAccount;
    }

    @Override
    @Transactional
    public void save(WxMpUser wxMpUser) {
        log.info("保存微信公众号用户信息：{}", wxMpUser.toString());
        ApiAssert.hasLength(wxMpUser.getUnionId(), WechatMpCode.Message.UNION_ID_NOT_NULL);
        ApiAssert.hasLength(wxMpUser.getOpenId(), WechatMpCode.Message.OPEN_ID_NOT_NULL);

        WechatOpenAccount wechatOpenAccount = wechatOpenAccountMapper.queryOne(WechatOpenAccountQO.builder().unionId(wxMpUser.getUnionId()).type(WechatOpenidType.PUBLIC).build());

        //保存公众号OpenId
        if (null == wechatOpenAccount) {
            wechatOpenAccount = WechatOpenAccount.builder()
                    .isSubscribe(Boolean.TRUE)
                    .unionId(wxMpUser.getUnionId())
                    .openId(wxMpUser.getOpenId())
                    .wxCode("MP_"+ UUID.randomUUID().toString())
                    .type(WechatOpenidType.PUBLIC)
                    .build();

            //判断是否用其他openId登录过
            List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountMapper.queryByUnionIdAndGuidNotNull(wxMpUser.getUnionId());
            if (CollectionUtils.isNotEmpty(wechatOpenAccounts)) {
                wechatOpenAccount.setGuid(wechatOpenAccounts.get(0).getGuid());
            }
            wechatOpenAccountMapper.create(wechatOpenAccount);
        } else if(null != wechatOpenAccount && wechatOpenAccount.getIsSubscribe() == Boolean.FALSE){
            wechatOpenAccount.setIsSubscribe(Boolean.TRUE);
            wechatOpenAccountMapper.update(wechatOpenAccount);
        }
    }

    @Override
    @Transactional
    public WechatOpenAccount update(WechatOpenAccount openAccount) {
        wechatOpenAccountMapper.update(openAccount);
        return openAccount;
    }

    @Override
    @Transactional
    public void updateGuidByUnionId(String guid, String unionId) {
        wechatOpenAccountMapper.updateGuidByUnionId(guid,unionId);
    }

    @Override
    public long countByUnionIdAndGuidNotNull(String unionId) {
        return wechatOpenAccountMapper.countByUnionIdAndGuidNotNull(unionId);
    }

    @Override
    public List<WechatOpenAccount> queryByUnionIdAndGuidNotNull(String unionId) {
        return wechatOpenAccountMapper.queryByUnionIdAndGuidNotNull(unionId);
    }

    @Override
    public WechatOpenAccount queryOne(WechatOpenAccountQO body) {
        return wechatOpenAccountMapper.queryOne(body);
    }

    @Override
    public List<WechatOpenAccount> queryList(WechatOpenAccountQO body) {
        return wechatOpenAccountMapper.queryList(body);
    }

}
