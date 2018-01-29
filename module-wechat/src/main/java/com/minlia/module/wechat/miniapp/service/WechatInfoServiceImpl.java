package com.minlia.module.wechat.miniapp.service;

import com.minlia.module.wechat.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.miniapp.entity.WechatOpenAccount;
import com.minlia.module.wechat.miniapp.enumeration.WechatOpenidType;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Created by will on 7/25/17.
 */
@Service
@Slf4j
@Transactional
public class WechatInfoServiceImpl implements WechatInfoService {

    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    public void save(WxMpUser wxMpUser) throws WxErrorException {
        WechatOpenAccount wechatOpenAccount = wechatOpenAccountService.queryOne(WechatOpenAccountQueryBody.builder().unionId(wxMpUser.getUnionId()).openType(WechatOpenidType.PUBLIC).build());

        //保存公众号OpenId
        if (null == wechatOpenAccount) {
            wechatOpenAccount = WechatOpenAccount.builder()
                    .isSubscribe(Boolean.TRUE)
                    .unionId(wxMpUser.getUnionId())
                    .openId(wxMpUser.getOpenId())
                    .wxCode("MP_"+UUID.randomUUID().toString())
                    .openType(WechatOpenidType.PUBLIC)
                    .build();

            //判断是否用其他openId登录过
            List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.findByUnionIdAndUserIdIsNotNull(wxMpUser.getUnionId());
            if (CollectionUtils.isNotEmpty(wechatOpenAccounts)) {
                wechatOpenAccount.setGuid(wechatOpenAccounts.get(0).getGuid());
            }
            wechatOpenAccountService.create(wechatOpenAccount);
        } else if(null != wechatOpenAccount && wechatOpenAccount.getIsSubscribe() == Boolean.FALSE){
            wechatOpenAccount.setIsSubscribe(Boolean.TRUE);
            wechatOpenAccountService.update(wechatOpenAccount);
        }
    }

}








