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
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author garen
 */
@Service
@Slf4j
public class WechatMaUserServiceImpl implements WechatMaUserService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WxMaUserMapper wxMaUserMapper;
    @Autowired
    private WechatMaService wechatMaService;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    @Transactional
    public WechatMaUser updateUserDetail(MiniappUserDetailRequestBody body) {
        WxMaService wxMaService = wechatMaService.getWxMaService(body.getType());

        WxMaJscode2SessionResult sessionResult = wechatMaService.getSessionInfo(wxMaService,body.getCode());
        WxMaUserInfo wxMaUserInfo = wxMaService.getUserService().getUserInfo(sessionResult.getSessionKey(),body.getEncryptedData(),body.getIv());
        WechatMaUser wechatMaUser = new WechatMaUser();
        BeanUtils.copyProperties(wxMaUserInfo,wechatMaUser);

        User user = SecurityContextHolder.getCurrentUser();
        //设置open信息
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryList(WechatOpenAccountQueryBody.builder().unionId(wechatMaUser.getUnionId()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setGuid(user.getGuid());
            wechatOpenAccount.setUnionId(wechatMaUser.getUnionId());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatMaUser wechatUserFind = wxMaUserMapper.queryByGuid(user.getGuid());
        if (null == wechatUserFind) {
            wechatMaUser.setGuid(user.getGuid());
            wxMaUserMapper.create(wechatMaUser);
        } else {
            wechatMaUser.setGuid(user.getGuid());
            wxMaUserMapper.update(wechatMaUser);
        }

        //发布更新微信用户详情事件 TODO
//        WechatMaUpdatedEvent.onUpdated(wechatMaUser);
        return wechatMaUser;
    }

    static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={access_token}&openid={openid}&lang=zh_cn";

    @Override
    @Async
    @Deprecated
    public WechatMaUser updateByOpenId(String guid, String openid) {
        String access_token = null;
        try {
            access_token = wxMpService.getAccessToken();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        String string = restTemplate.getForObject(GET_USER_INFO,String.class,access_token,openid);

        WechatMaUser wxMpUser = restTemplate.getForObject(GET_USER_INFO,WechatMaUser.class,access_token,openid);

        //设置open信息
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryList(WechatOpenAccountQueryBody.builder().unionId(wxMpUser.getUnionId()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setGuid(guid);
            wechatOpenAccount.setUnionId(wxMpUser.getUnionId());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatMaUser wechatUser = wxMaUserMapper.queryByGuid(guid);
        if (null == wechatUser) {
            wechatUser.setGuid(guid);
            wxMaUserMapper.create(wechatUser);
        } else {
            wechatUser.setGuid(guid);
            wxMaUserMapper.update(wechatUser);
        }

        //发布更新微信用户详情事件 TODO
        WechatMaUpdatedEvent.onUpdated(new WechatMaUpdatedEvent(wechatUser));
        return wechatUser;
    }

    @Override
    public WechatMaUser showUserDetail() {
        User user = SecurityContextHolder.getCurrentUser();
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);
        return wxMaUserMapper.queryByGuid(user.getGuid());
    }

}
