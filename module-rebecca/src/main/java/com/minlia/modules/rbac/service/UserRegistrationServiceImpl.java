package com.minlia.modules.rbac.service;

import com.google.common.collect.Sets;
import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.rbac.bean.to.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.bean.to.UserRegistrationRequestBody;
import com.minlia.modules.security.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    private UserService userService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserQueryService userQueryService;

    @Override
    public User registration(UserRegistrationRequestBody body) {
        switch (body.getType()) {
            case USERNAME:
                //用户名注册：没有验证码机制，可能存在恶意注册，暂时废弃
                ApiPreconditions.is(true, ApiCode.BASED_ON,"暂不支持用户名注册");
                break;
            case CELLPHONE:
                captchaService.validity(body.getCellphone(),body.getCode());
                break;
            case EMAIL:
                ApiPreconditions.is(true, ApiCode.BASED_ON,"暂不支持邮箱注册");
                captchaService.validity(body.getCellphone(),body.getCode());
                break;
        }

        User user = userService.create(UserCreateRequestBody.builder()
                .username(body.getUsername())
                .cellphone(body.getUsername())
                .password(body.getPassword())
                .defaultRole(SecurityConstant.ROLE_USER_CODE)
                .roles(Sets.newHashSet(SecurityConstant.ROLE_USER_ID))
                .referral(body.getReferral())
                .build());
        return user;
    }

    @Override
    public StatefulBody availablitity(UserAvailablitityRequestBody body) {
        //正则校验 TODO
        if (userQueryService.exists(UserQueryRequestBody.builder().username(body.getUsername()).build())) {
            return FailureResponseBody.builder().message("账号已存在").build();
        } else {
            return SuccessResponseBody.builder().message("Available").build();
        }
    }

}
