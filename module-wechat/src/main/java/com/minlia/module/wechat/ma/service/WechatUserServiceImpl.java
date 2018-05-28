package com.minlia.module.wechat.ma.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.wechat.ma.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.ma.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.ma.body.WechatSession;
import com.minlia.module.wechat.ma.entity.WechatOpenAccount;
import com.minlia.module.wechat.ma.entity.WechatUser;
import com.minlia.module.wechat.ma.event.WechatDetailUpdatedEvent;
import com.minlia.module.wechat.ma.mapper.WechatUserMapper;
import com.minlia.module.wechat.wechat.miniapp.WechatSessionRemoteApi;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author garen
 */
@Service
@Slf4j
public class WechatUserServiceImpl implements WechatUserService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private WechatMiniappService wechatMiniappService;
    @Autowired
    private WechatUserMapper wechatUserDetailMapper;
    @Autowired
    private WechatSessionRemoteApi wechatSessionRemoteApi;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    @Override
    public WechatUser updateUserDetail(MiniappUserDetailRequestBody body) {
        WechatSession session = wechatMiniappService.getSessionInfo(body.getType(),body.getCode());

        WechatUser wechatUserDetail = wechatSessionRemoteApi.decryptUserInfo(body.getEncryptedData(), body.getIv(), session.getSessionKey());
        log.info("session_key -> {} openid -> {} expires_in -> {} userinfo ->{}", session.getSessionKey(), session.getOpenid(), session.getExpiresin(), wechatUserDetail);
        ApiPreconditions.checkNotNull(wechatUserDetail, ApiCode.NOT_FOUND);//从官方接口没取到对应用户的详情, 如网络问题

        User user = SecurityContextHolder.getCurrentUser();
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);

        //设置open信息
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryList(WechatOpenAccountQueryBody.builder().unionId(wechatUserDetail.getUnionid()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setGuid(user.getGuid());
            wechatOpenAccount.setUnionId(wechatUserDetail.getUnionid());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatUser wechatUser = wechatUserDetailMapper.queryByGuid(user.getGuid());
        if (null == wechatUser) {
            wechatUser = mapper.map(wechatUserDetail,WechatUser.class);
            wechatUser.setGuid(user.getGuid());
            wechatUserDetailMapper.create(wechatUser);
        } else {
            mapper.map(wechatUserDetail, wechatUser);
            wechatUserDetailMapper.update(wechatUser);
        }

        //发布更新微信用户详情事件 TODO
        WechatDetailUpdatedEvent.onUpdated(new WechatDetailUpdatedEvent(wechatUser));

        return wechatUser;
    }

    static String GET_USER_INFO = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={access_token}&openid={openid}&lang=zh_cn";

    @Override
    @Async
    public WechatUser updateByOpenId(String guid, String openid) {
        String access_token = null;
        try {
            access_token = wxMpService.getAccessToken();
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        String string = restTemplate.getForObject(GET_USER_INFO,String.class,access_token,openid);

        WechatUser wxMpUser = restTemplate.getForObject(GET_USER_INFO,WechatUser.class,access_token,openid);

        //设置open信息
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryList(WechatOpenAccountQueryBody.builder().unionId(wxMpUser.getUnionid()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setGuid(guid);
            wechatOpenAccount.setUnionId(wxMpUser.getUnionid());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatUser wechatUser = wechatUserDetailMapper.queryByGuid(guid);
        if (null == wechatUser) {
            wechatUser.setGuid(guid);
            wechatUserDetailMapper.create(wechatUser);
        } else {
            wechatUser.setGuid(guid);
            wechatUserDetailMapper.update(wechatUser);
        }

        //发布更新微信用户详情事件 TODO
        WechatDetailUpdatedEvent.onUpdated(new WechatDetailUpdatedEvent(wechatUser));
        return wechatUser;
    }

    @Override
    public WechatUser showUserDetail() {
        User user = SecurityContextHolder.getCurrentUser();
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);
        return wechatUserDetailMapper.queryByGuid(user.getGuid());
    }

}
