package com.minlia.modules.rbac.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.modules.rbac.bean.to.PasswordByCaptchaChangeTO;
import com.minlia.modules.rbac.bean.to.PasswordByRawPasswordChangeTO;
import com.minlia.modules.rbac.bean.to.PasswordResetTO;
import com.minlia.modules.rbac.constant.RebaccaCode;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.mapper.UserMapper;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserPasswordServiceImpl implements UserPasswordService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User forget(PasswordResetTO body) {
        //验证验证码是否正确
        captchaService.validityByCellphone(body.getUsername(), body.getCode());

        User user = userMapper.queryOne(UserQO.builder().username(body.getUsername()).build());
        ApiAssert.notNull(user, RebaccaCode.Message.USER_NOT_EXISTED);
        return change(user,body.getNewPassword());
    }

    @Override
    public User change(PasswordByCaptchaChangeTO body) {
        User user = SecurityContextHolder.getCurrentUser();

        //验证验证码是否正确
        captchaService.validityByCellphone(user.getUsername(), body.getCode());

        return change(user,body.getNewPassword());
    }

    @Override
    public User change(PasswordByRawPasswordChangeTO body) {
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
        user.setLockLimit(0);
        user.setLockTime(new Date());
        userMapper.update(user);
        return user;
    }

}
