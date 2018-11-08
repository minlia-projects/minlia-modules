package com.minlia.modules.rbac.service;

import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.constant.RebaccaCode;
import com.minlia.modules.rbac.bean.to.UserCTO;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.to.UserRegistrationTO;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.to.UserAvailablitityTO;
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
                captchaService.validityByCellphone(to.getCellphone(), to.getCode());
                break;
            case EMAIL:
                captchaService.validityByEmail(to.getEmail(),to.getCode());
                break;
        }

        User user = userService.create(UserCTO.builder()
                .method(to.getType())
                .username(to.getUsername())
                .cellphone(to.getCellphone())
                .email(to.getEmail())
                .password(to.getPassword())
                .defaultRole(SecurityConstant.ROLE_USER_ID)
                .roles(Sets.newHashSet(SecurityConstant.ROLE_USER_ID))
                .referral(to.getReferral())
                .build());
        return user;
    }

    @Override
    public Response availablitity(UserAvailablitityTO body) {
        switch (body.getMethod()) {
            case USERNAME:
                ApiAssert.hasLength(body.getUsername(), RebaccaCode.Message.USERNAME_NOT_NULL);
                ApiAssert.state(!userQueryService.exists(UserQO.builder().username(body.getUsername()).build()), RebaccaCode.Message.USERNAME_ALREADY_EXISTED);
                break;
            case CELLPHONE:
                ApiAssert.hasLength(body.getCellphone(), RebaccaCode.Message.USERNAME_NOT_NULL);
                ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(body.getCellphone()).build()), RebaccaCode.Message.USER_CELLPHONE_ALREADY_EXISTED);
                break;
            case EMAIL:
                ApiAssert.hasLength(body.getEmail(), RebaccaCode.Message.USERNAME_NOT_NULL);
                ApiAssert.state(!userQueryService.exists(UserQO.builder().email(body.getEmail()).build()), RebaccaCode.Message.USER_EMAIL_ALREADY_EXISTED);
                break;
        }
        return Response.success("Available");
    }

}
