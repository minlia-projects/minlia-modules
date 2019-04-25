package com.minlia.module.wechat.login.service.impl;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wechat.login.entity.WechatUser;
import com.minlia.module.wechat.login.mapper.WechatUserMapper;
import com.minlia.module.wechat.login.ro.WechatUserQO;
import com.minlia.module.wechat.login.service.WechatUserService;
import com.minlia.module.wechat.ma.enumeration.WechatOpenidType;
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
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private WechatUserMapper wechatUserMapper;

    @Override
    @Transactional
    public WechatUser create(WechatUser wechatUser) {
        wechatUserMapper.create(wechatUser);
        return wechatUser;
    }

    @Override
    @Transactional
    public void save(WxMpUser wxMpUser) {
        log.info("保存微信公众号用户信息：{}", wxMpUser.toString());
//        ApiAssert.hasLength(wxMpUser.getUnionId(), WechatMpCode.Message.UNION_ID_NOT_NULL);
        ApiAssert.hasLength(wxMpUser.getOpenId(), WechatMpCode.Message.OPEN_ID_NOT_NULL);

        WechatUser wechatUser = wechatUserMapper.queryOne(WechatUserQO.builder().unionId(wxMpUser.getUnionId()).type(WechatOpenidType.PUBLIC).build());

        //保存公众号OpenId
        if (null == wechatUser) {
            wechatUser = WechatUser.builder()
                    .isSubscribe(wxMpUser.getSubscribe())
                    .unionId(wxMpUser.getUnionId())
                    .openId(wxMpUser.getOpenId())
                    .wxCode("MP_"+ UUID.randomUUID().toString())
                    .type(WechatOpenidType.PUBLIC)
                    .build();

            //判断是否用其他openId登录过
            List<WechatUser> wechatUsers = wechatUserMapper.queryByUnionIdAndGuidNotNull(wxMpUser.getUnionId());
            if (CollectionUtils.isNotEmpty(wechatUsers)) {
                wechatUser.setGuid(wechatUsers.get(0).getGuid());
            }
            wechatUserMapper.create(wechatUser);
        } else if(null != wechatUser && wechatUser.getIsSubscribe() == Boolean.FALSE){
            wechatUser.setIsSubscribe(wxMpUser.getSubscribe());
            wechatUserMapper.update(wechatUser);
        }
    }

    @Override
    @Transactional
    public WechatUser update(WechatUser wechatUser) {
        wechatUserMapper.update(wechatUser);
        return wechatUser;
    }

    @Override
    @Transactional
    public void updateGuidByUnionId(String guid, String unionId) {
        wechatUserMapper.updateGuidByUnionId(guid,unionId);
    }

    @Override
    public long countByUnionIdAndGuidNotNull(String unionId) {
        return wechatUserMapper.countByUnionIdAndGuidNotNull(unionId);
    }

    @Override
    public List<WechatUser> queryByUnionIdAndGuidNotNull(String unionId) {
        return wechatUserMapper.queryByUnionIdAndGuidNotNull(unionId);
    }

    @Override
    public WechatUser queryOne(WechatUserQO body) {
        return wechatUserMapper.queryOne(body);
    }

    @Override
    public List<WechatUser> queryList(WechatUserQO body) {
        return wechatUserMapper.queryList(body);
    }

}
