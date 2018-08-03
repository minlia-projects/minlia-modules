package com.minlia.module.wechat.ma.service;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.binarywang.wx.miniapp.bean.WxMaUserInfo;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.entity.WechatMaUser;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.ma.event.WechatMaUpdatedEvent;
import com.minlia.module.wechat.ma.mapper.WxMaUserMapper;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
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
    private WxMaUserMapper wxMaUserMapper;
    @Autowired
    private WechatMaService wechatMaService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    @Transactional
    public WechatMaUser update(MiniappUserDetailRequestBody body) {
        WxMaService wxMaService = wechatMaService.getWxMaService(body.getType());
        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(wxMaService,body.getCode());
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionResult.getSessionKey(),body.getEncryptedData(),body.getIv());
        User user = SecurityContextHolder.getCurrentUser();
        //设置open信息
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryList(WechatOpenAccountQueryBody.builder().unionId(wxMaUserInfo.getUnionId()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setGuid(user.getGuid());
            wechatOpenAccount.setUnionId(wxMaUserInfo.getUnionId());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatMaUser wechatMaUser = wxMaUserMapper.queryByGuid(user.getGuid());
        if (null == wechatMaUser) {
            wechatMaUser = mapper.map(wxMaUserInfo,WechatMaUser.class);
            wechatMaUser.setGuid(user.getGuid());
            wxMaUserMapper.create(wechatMaUser);
        } else {
            mapper.map(wxMaUserInfo,wechatMaUser);
            wechatMaUser.setGuid(user.getGuid());
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
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);
        return wxMaUserMapper.queryByGuid(user.getGuid());
    }

}
