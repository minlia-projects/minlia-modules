package com.minlia.module.tencent.miniapp.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.tencent.miniapp.body.MiniappUserDetailRequestBody;
import com.minlia.module.tencent.miniapp.body.WechatOpenAccountQueryBody;
import com.minlia.module.tencent.miniapp.body.WechatSession;
import com.minlia.module.tencent.miniapp.domain.WechatOpenAccount;
import com.minlia.module.tencent.miniapp.domain.WechatUserDetail;
import com.minlia.module.tencent.miniapp.event.WechatDetailUpdatedEvent;
import com.minlia.module.tencent.miniapp.repository.WechatUserDetailRepository;
import com.minlia.module.tencent.miniapp.wechat.miniapp.WechatSessionRemoteApi;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import com.minlia.modules.rbac.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WechatUserDetailServiceImpl implements WechatUserDetailService {

    @Autowired
    private WechatUserDetailRepository repository;

    @Autowired
    private Mapper mapper;
    @Autowired
    private WechatMiniappService wechatMiniappService;
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
        List<WechatOpenAccount> wechatOpenAccounts = wechatOpenAccountService.findList(WechatOpenAccountQueryBody.builder().unionId(wechatUserDetail.getUnionId()).build());
        for (WechatOpenAccount wechatOpenAccount:wechatOpenAccounts) {
            wechatOpenAccount.setUserId(user.getId());
            wechatOpenAccount.setUnionId(wechatUserDetail.getUnionId());
            wechatOpenAccountService.update(wechatOpenAccount);
        }

        //保存用户详情
        WechatUserDetail wechatUserDetailOrigin = repository.findByUserId(user.getId());
        if (null == wechatUserDetailOrigin) {
            wechatUserDetailOrigin = mapper.map(wechatUserDetail,WechatUserDetail.class);
        } else {
            wechatUserDetailOrigin.setOpenId(wechatUserDetail.getOpenId());
            wechatUserDetailOrigin.setNickName(wechatUserDetail.getNickName());
            wechatUserDetailOrigin.setGender(wechatUserDetail.getGender());
            wechatUserDetailOrigin.setProvince(wechatUserDetail.getProvince());
            wechatUserDetailOrigin.setCity(wechatUserDetail.getCity());
            wechatUserDetailOrigin.setCountry(wechatUserDetail.getCountry());
            wechatUserDetailOrigin.setAvatarUrl(wechatUserDetail.getAvatarUrl());
        }
        wechatUserDetailOrigin.setUserId(user.getId());
        repository.save(wechatUserDetailOrigin);

        //发布更新微信用户详情事件 TODO
        WechatDetailUpdatedEvent.onUpdated(new WechatDetailUpdatedEvent(wechatUserDetailOrigin));

        return SuccessResponseBody.builder().message("OK").build();
    }

    @Override
    public WechatUserDetail showUserDetail() {
        User user = SecurityContextHolder.getCurrentUser();
        ApiPreconditions.checkNotNull(user, ApiCode.INVALID_CREDENTIAL);
        return repository.findByUserId(user.getId());
    }

}
