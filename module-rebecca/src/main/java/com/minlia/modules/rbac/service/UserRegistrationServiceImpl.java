package com.minlia.modules.rbac.service;

import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
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
                ApiAssert.state(false, RebaccaCode.Message.USER_UNSUPPORTED_USERNAME_REGISTERED);
                break;
            case CELLPHONE:
                captchaService.validity(body.getCellphone(),body.getCode());
                break;
            case EMAIL:
                ApiAssert.state(false, RebaccaCode.Message.USER_UNSUPPORTED_EMAIL_REGISTERED);
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
    public Response availablitity(UserAvailablitityRequestBody body) {
        if (userQueryService.exists(UserQueryRequestBody.builder().username(body.getUsername()).build())) {
            return Response.failure(RebaccaCode.Message.USER_ALREADY_EXISTS);
        } else {
            return Response.success("Available");
        }
    }

}
