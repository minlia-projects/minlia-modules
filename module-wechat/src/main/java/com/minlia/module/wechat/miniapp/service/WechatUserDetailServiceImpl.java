package com.minlia.module.wechat.miniapp.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.wechat.miniapp.body.MiniappUserDetailRequestBody;
import com.minlia.module.wechat.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.wechat.miniapp.body.WechatSession;
import com.minlia.module.wechat.miniapp.entity.WechatOpenAccount;
import com.minlia.module.wechat.miniapp.entity.WechatUserDetail;
import com.minlia.module.wechat.miniapp.event.WechatDetailUpdatedEvent;
import com.minlia.module.wechat.miniapp.mapper.WechatUserDetailMapper;
import com.minlia.module.wechat.wechat.miniapp.WechatSessionRemoteApi;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WechatUserDetailServiceImpl implements WechatUserDetailService {

    @Autowired
    private Mapper mapper;
    @Autowired
    private WechatMiniappService wechatMiniappService;
    @Autowired
    private WechatUserDetailMapper wechatUserDetailMapper;
    @Autowired
    private WechatSessionRemoteApi wechatSessionRemoteApi;
    @Autowired
    private WechatOpenAccountService wechatOpenAccountService;

    public StatefulBody updateUserDetail(MiniappUserDetailRequestBody body) {
        WechatSession session = wechatMiniappService.getSessionInfo(body.getType(),body.getCode());

        WechatUserDetail wechatUserDetail = wechatSessionRemoteApi.decryptUserInfo(body.getEncryptedData(), body.getIv(), session.getSessionKey());
        log.info("session_key -> {} openid -> {} expires_in -> {} userinfo ->{}", session.getSessionKey(), session.getOpenid(), session.getExpiresin(), wechatUserDetail);
        ApiPreconditions.checkNotNull(wechatUserDetail, ApiCode.NOT_FOUND);//从官方接口没取到对应用户的详情, 如网络问题

        User user = SecurityContextHolder.getCurrentUser();
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);

        //设置open信息
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.queryList(WechatOpenAccountQueryBody.builder().unionId(wechatUserDetail.getUnionId()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setGuid(user.getGuid());
            wechatOpenAccount.setUnionId(wechatUserDetail.getUnionId());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatUserDetail wechatUserDetailOrigin = wechatUserDetailMapper.queryByGuid(user.getGuid());
        if (null == wechatUserDetailOrigin) {
            wechatUserDetailOrigin = mapper.map(wechatUserDetail,WechatUserDetail.class);
            wechatUserDetailOrigin.setGuid(user.getGuid());
            wechatUserDetailMapper.create(wechatUserDetailOrigin);
        } else {
//            wechatUserDetailOrigin.setOpenId(wechatUserDetail.getOpenId());
//            wechatUserDetailOrigin.setNickName(wechatUserDetail.getNickName());
//            wechatUserDetailOrigin.setGender(wechatUserDetail.getGender());
//            wechatUserDetailOrigin.setProvince(wechatUserDetail.getProvince());
//            wechatUserDetailOrigin.setCity(wechatUserDetail.getCity());
//            wechatUserDetailOrigin.setCountry(wechatUserDetail.getCountry());
//            wechatUserDetailOrigin.setAvatarUrl(wechatUserDetail.getAvatarUrl());
            mapper.map(wechatUserDetail,wechatUserDetailOrigin);
            wechatUserDetailMapper.update(wechatUserDetailOrigin);
        }

        //发布更新微信用户详情事件 TODO
        WechatDetailUpdatedEvent.onUpdated(new WechatDetailUpdatedEvent(wechatUserDetailOrigin));

        return SuccessResponseBody.builder().message("OK").build();
    }

    @Override
    public WechatUserDetail showUserDetail() {
        User user = SecurityContextHolder.getCurrentUser();
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);
        return wechatUserDetailMapper.queryByGuid(user.getGuid());
    }

}
