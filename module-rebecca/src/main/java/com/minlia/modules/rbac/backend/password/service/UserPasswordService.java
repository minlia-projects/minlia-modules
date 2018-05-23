package com.minlia.modules.rbac.backend.password.service;


import com.minlia.modules.rbac.backend.password.body.ChangePasswordByCaptchaRequestBody;
import com.minlia.modules.rbac.backend.password.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.backend.password.body.ResetPasswordRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserPasswordService {

    /**
     * 忘记密码
     * @return
     */
    User forget(ResetPasswordRequestBody body);

    /**
     * 根据验证码修改密码
     * @param body
     * @return
     */
    User change(ChangePasswordByCaptchaRequestBody body);

    /**
     * 根据原密码修改密码
     * @param body
     * @return
     */
    User change(ChangePasswordByRawPasswordRequestBody body);

}
