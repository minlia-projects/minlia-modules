package com.minlia.modules.rebecca.service.impl;

import com.google.common.collect.Sets;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.bean.to.UserAvailablitityTO;
import com.minlia.modules.rebecca.bean.to.UserCTO;
import com.minlia.modules.rebecca.bean.to.UserRegistrationTO;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.rebecca.service.UserRegistrationService;
import com.minlia.modules.rebecca.service.UserService;
import com.minlia.modules.security.constant.SecurityConstant;
import org.apache.commons.lang3.StringUtils;
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
        UserCTO userCTO = UserCTO.builder()
                .password(to.getPassword())
                .defaultRole(SecurityConstant.ROLE_USER_CODE)
                .roles(Sets.newHashSet(SecurityConstant.ROLE_USER_CODE))
                .referral(to.getReferral())
                .nickname(to.getNickname())
                .build();

        switch (to.getType()) {
            case USERNAME:
                ApiAssert.state(false, UserCode.Message.UNSUPPORTED_USERNAME_REGISTERED);
                userCTO.setUsername(to.getUsername());
                break;
            case CELLPHONE:
                captchaService.validityByCellphone(to.getCellphone(), to.getCode());
                userCTO.setCellphone(to.getCellphone());
                break;
            case EMAIL:
                captchaService.validityByEmail(to.getEmail(), to.getCode());
                userCTO.setEmail(to.getEmail());
                break;
        }
        return userService.create(userCTO);
    }

    @Override
    public Response availablitity(UserAvailablitityTO body) {
        if (StringUtils.isNotBlank(body.getUsername()) && userQueryService.exists(UserQO.builder().username(body.getUsername()).build())) {
            return Response.failure(UserCode.Message.USERNAME_ALREADY_EXISTS);
        }
        if (StringUtils.isNotBlank(body.getCellphone()) && userQueryService.exists(UserQO.builder().cellphone(body.getCellphone()).build())) {
            return Response.failure(UserCode.Message.CELLPHONE_ALREADY_EXISTS);
        }
        if (StringUtils.isNotBlank(body.getEmail()) && userQueryService.exists(UserQO.builder().email(body.getEmail()).build())) {
            return Response.failure(UserCode.Message.EMAIL_ALREADY_EXISTS);
        }
        return Response.success("Available");
    }

}
