package com.minlia.modules.rbac.backend.password.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
import com.minlia.modules.rbac.backend.password.body.ChangePasswordByCaptchaRequestBody;
import com.minlia.modules.rbac.backend.password.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.backend.password.body.ResetPasswordRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User forget(ResetPasswordRequestBody body) {
        //验证验证码是否正确
        captchaService.validity(body.getUsername(), body.getCode());

        User user = userMapper.queryOne(UserQueryRequestBody.builder().username(body.getUsername()).build());
        ApiAssert.notNull(null, RebaccaCode.Message.USER_NOT_EXISTED);
        return change(user,body.getNewPassword());
    }

    @Override
    public User change(ChangePasswordByCaptchaRequestBody body) {
        User user= SecurityContextHolder.getCurrentUser();

        //验证验证码是否正确
        captchaService.validity(user.getUsername(), body.getCode());

        return change(user,body.getNewPassword());
    }

    @Override
    public User change(ChangePasswordByRawPasswordRequestBody body) {
        User user= SecurityContextHolder.getCurrentUser();

        Boolean bool = bCryptPasswordEncoder.matches(body.getRawPassword(),user.getPassword());
        ApiAssert.state(!bool, RebaccaCode.Message.USER_RAW_PASSWORD_ERROR);
        return change(user,body.getNewPassword());
    }

    private User change(User user,String newPassword) {
        //设置新密码
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        user.setEnabled(Boolean.TRUE);
        user.setCredentialsExpired(Boolean.FALSE);
        user.setLocked(Boolean.FALSE);
        userMapper.update(user);
        return user;
    }

}
