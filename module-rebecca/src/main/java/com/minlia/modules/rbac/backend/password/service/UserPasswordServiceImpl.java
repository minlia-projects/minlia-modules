package com.minlia.modules.rbac.backend.password.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.captcha.service.CaptchaService;
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

        User user = userMapper.queryOne(UserQueryRequestBody.builder().guid(body.getUsername()).build());
        ApiPreconditions.is(user == null, ApiCode.NOT_FOUND,"该用户尚未注册");
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
        ApiPreconditions.not(bool, ApiCode.INVALID_RAW_PASSWORD,"原密码错误，请确认！");

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
