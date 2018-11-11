package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.wechat.ma.bean.to.MiniappUserDetailTO;
import com.minlia.module.wechat.ma.bean.qo.WechatMaUserQO;
import com.minlia.module.wechat.ma.bean.domain.WechatMaUser;
import com.minlia.module.wechat.ma.event.WechatMaUpdatedEvent;
import com.minlia.module.wechat.ma.mapper.WxMaUserMapper;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author garen
 */
@Service
@Slf4j
public class WechatMaUserServiceImpl implements WechatMaUserService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private WxMaUserMapper wxMaUserMapper;
    @Autowired
    private WechatMaService wechatMaService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public WechatMaUser update(WechatMaUser wechatMaUser) {
        //绑定用户GUID
        wechatOpenAccountService.updateGuidByUnionId(wechatMaUser.getGuid(), wechatMaUser.getUnionId());
        wxMaUserMapper.update(wechatMaUser);
        return wechatMaUser;
    }

    @Override
    @Transactional
    public WechatMaUser update(MiniappUserDetailTO to) {
        WxMaService wxMaService = wechatMaService.getWxMaService(to.getType());
        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(wxMaService,to.getCode());
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionResult.getSessionKey(),to.getEncryptedData(),to.getIv());
        return this.update(wxMaUserInfo, to.getCode(), SecurityContextHolder.getCurrentGuid());
    }

    @Override
    @Async
    public WechatMaUser update(WxMaUserInfo wxMaUserInfo, String code, String guid) {
        if (null != guid) {
            //绑定用户GUID
            wechatOpenAccountService.updateGuidByUnionId(guid, wxMaUserInfo.getUnionId());
        }

        //保存用户详情
        WechatMaUser wechatMaUser = wxMaUserMapper.queryOne(WechatMaUserQO.builder().unionId(wxMaUserInfo.getUnionId()).build());
        if (null == wechatMaUser) {
            wechatMaUser = mapper.map(wxMaUserInfo,WechatMaUser.class);
            wechatMaUser.setGuid(guid);
            wechatMaUser.setCode(code);
            wxMaUserMapper.create(wechatMaUser);
        } else {
            mapper.map(wxMaUserInfo,wechatMaUser);
            wechatMaUser.setGuid(guid);
            wechatMaUser.setCode(code);
            wxMaUserMapper.update(wechatMaUser);
        }

        //发布更新微信用户详情事件
        WechatMaUpdatedEvent.onUpdated(wechatMaUser);
        return wechatMaUser;
    }

    static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={access_token}&openid={openid}&lang=zh_cn";

    @Override
    public WechatMaUser me() {
        User user = SecurityContextHolder.getCurrentUser();
        ApiAssert.notNull(user, SystemCode.Message.DATA_NOT_EXISTS);
        return wxMaUserMapper.queryOne(WechatMaUserQO.builder().guid(user.getGuid()).build());
    }

    @Override
    public WechatMaUser queryOne(WechatMaUserQO qo) {
        return wxMaUserMapper.queryOne(qo);
    }

    @Override
    public WxMaUserInfo decrypt(MiniappUserDetailTO body) {
        WxMaService wxMaService = wechatMaService.getWxMaService(body.getType());
        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(wxMaService,body.getCode());
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionResult.getSessionKey(), body.getEncryptedData(), body.getIv());
        return wxMaUserInfo;
    }

}
