package com.minlia.module.wechat.ma.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wechat.login.service.WechatUserService;
import com.minlia.module.wechat.ma.bean.entity.WechatMaUser;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.bean.ro.WechatMaUserDetailRO;
import com.minlia.module.wechat.ma.event.WechatMaUpdatedEvent;
import com.minlia.module.wechat.ma.mapper.WechatMaUserMapper;
import com.minlia.module.wechat.ma.service.WechatMaService;
import com.minlia.module.wechat.ma.service.WechatMaUserService;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author garen
 */
@Service
@Slf4j
public class WechatMaUserServiceImpl implements WechatMaUserService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private WechatMaService wechatMaService;
    @Autowired
    private WechatMaUserMapper wechatMaUserMapper;
    @Autowired
    private WechatUserService wechatUserService;

    @Override
    @Transactional
    public WechatMaUser update(WechatMaUser wechatMaUser) {
        if (null != wechatMaUser.getUnionId() && StringUtils.isNotBlank(wechatMaUser.getGuid())) {
            wechatUserService.updateGuidByUnionId(wechatMaUser.getGuid(), wechatMaUser.getUnionId());
        }
        wechatMaUserMapper.update(wechatMaUser);
        return wechatMaUser;
    }

    @Override
    @Transactional
    public WechatMaUser update(WechatMaUserDetailRO ro) {
        WxMaUserInfo wxMaUserInfo = wechatMaService.decrypt(ro);
        return this.update(wxMaUserInfo, ro.getCode(), SecurityContextHolder.getCurrentGuid());
    }

    @Override
    @Transactional
    public WechatMaUser update(WxMaUserInfo wxMaUserInfo, String code, String guid) {
        log.error("更新小程序用户信息-------------------------------------------------");
        if (null != guid && null != wxMaUserInfo.getUnionId()) {
            wechatUserService.updateGuidByUnionId(guid, wxMaUserInfo.getUnionId());
        }

        //保存用户详情
        WechatMaUser wechatMaUser = wechatMaUserMapper.queryOne(WechatMaUserQO.builder().openId(wxMaUserInfo.getOpenId()).build());
        if (null == wechatMaUser) {
            wechatMaUser = mapper.map(wxMaUserInfo, WechatMaUser.class);
            wechatMaUser.setGuid(guid);
            wechatMaUser.setCode(code);
            wechatMaUserMapper.create(wechatMaUser);
        } else {
            mapper.map(wxMaUserInfo, wechatMaUser);
            wechatMaUser.setGuid(guid);
            wechatMaUser.setCode(code);
            wechatMaUserMapper.update(wechatMaUser);
        }

        //发布更新微信用户详情事件
        WechatMaUpdatedEvent.onUpdated(wechatMaUser);
        return wechatMaUser;
    }

    @Override
    public WechatMaUser me() {
        User user = SecurityContextHolder.getCurrentUser();
        ApiAssert.notNull(user, SystemCode.Message.DATA_NOT_EXISTS);
        return wechatMaUserMapper.queryOne(WechatMaUserQO.builder().guid(user.getGuid()).build());
    }

    @Override
    public WechatMaUser queryOne(WechatMaUserQO qo) {
        return wechatMaUserMapper.queryOne(qo);
    }

    @Override
    public List<WechatMaUser> queryList(WechatMaUserQO qo) {
        return wechatMaUserMapper.queryList(qo);
    }

    @Override
    public int deleteByCodeAndGuidIsNull(String code) {
        return wechatMaUserMapper.deleteByCodeAndGuidIsNull(code);
    }
}
