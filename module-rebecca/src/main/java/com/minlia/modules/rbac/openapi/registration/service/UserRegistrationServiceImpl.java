package com.minlia.modules.rbac.openapi.registration.service;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.FailureResponseBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.service.UserQueryService;
import com.minlia.modules.rbac.backend.user.service.UserService;
import com.minlia.modules.rbac.openapi.registration.body.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.openapi.registration.body.UserRegistrationRequestBody;
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
        User user = userService.create(UserCreateRequestBody.builder()
                .username(body.getUsername())
                .cellphone(body.getUsername())
                .password(body.getPassword())
                .referral(body.getReferral())
                .build());

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
//    RegistrationEvent.onCompleted(user);
        return user;
    }

    @Override
    public StatefulBody availablitity(UserAvailablitityRequestBody body) {
        //正则校验 TODO

        if (userQueryService.exists(body.getUsername())) {
            return FailureResponseBody.builder().message("账号已存在").build();
        } else {
            return SuccessResponseBody.builder().message("Available").build();
        }
    }

}
