package com.minlia.module.wechat.miniapp.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaPhoneNumberInfo;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.wechat.miniapp.entity.WxMaUserInfoEntity;
import com.minlia.module.wechat.miniapp.mapper.WxMaUserInfoMapper;
import com.minlia.module.wechat.miniapp.service.WxMaUserInfoService;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * <p>
 * 微信小程序信息 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
@Service
public class WxMaUserInfoServiceImpl extends ServiceImpl<WxMaUserInfoMapper, WxMaUserInfoEntity> implements WxMaUserInfoService {

    @Override
    public WxMaUserInfoEntity save(WxMaUserInfo userInfo, WxMaPhoneNumberInfo phoneNumberInfo) {
        WxMaUserInfoEntity entity = this.getByOpenId(userInfo.getOpenId());
        if (Objects.nonNull(entity)) {
            DozerUtils.map(userInfo, entity);
            entity.setAppid(userInfo.getWatermark().getAppid());
            entity.setTimestamp(userInfo.getWatermark().getTimestamp());
            this.updateById(entity);
        } else {
            entity = DozerUtils.map(userInfo, WxMaUserInfoEntity.class);
            entity.setAppid(userInfo.getWatermark().getAppid());
            entity.setTimestamp(userInfo.getWatermark().getTimestamp());
            entity.setPhoneNumber(phoneNumberInfo.getCountryCode() + phoneNumberInfo.getPurePhoneNumber());
            this.save(entity);
        }
        return entity;
    }

    @Override
    public WxMaUserInfoEntity getByOpenId(String openId) {
        return this.getOne(Wrappers.<WxMaUserInfoEntity>lambdaQuery().eq(WxMaUserInfoEntity::getOpenId, openId));
    }

}