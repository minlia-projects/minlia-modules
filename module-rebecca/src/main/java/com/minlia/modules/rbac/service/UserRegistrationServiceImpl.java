package com.minlia.modules.rbac.service;

import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
import com.minlia.modules.rbac.backend.user.body.UserCTO;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserRegistrationTO;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.rbac.bean.to.UserAvailablitityRequestBody;
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
    public User registration(UserRegistrationTO to) {
        switch (to.getType()) {
            case USERNAME:
                ApiAssert.state(false, RebaccaCode.Message.USER_UNSUPPORTED_USERNAME_REGISTERED);
                break;
            case CELLPHONE:
                captchaService.validity(to.getCellphone(), to.getCode());
                break;
            case EMAIL:
                ApiAssert.state(false, RebaccaCode.Message.USER_UNSUPPORTED_EMAIL_REGISTERED);
                captchaService.validity(to.getCellphone(),to.getCode());
                break;
        }

        User user = userService.create(UserCTO.builder()
                .method(to.getType())
                .username(to.getUsername())
                .cellphone(to.getUsername())
                .password(to.getPassword())
                .defaultRole(SecurityConstant.ROLE_USER_ID)
                .roles(Sets.newHashSet(SecurityConstant.ROLE_USER_ID))
                .referral(to.getReferral())
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
