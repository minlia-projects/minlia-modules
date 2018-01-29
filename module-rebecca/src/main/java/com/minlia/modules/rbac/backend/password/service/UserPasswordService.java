package com.minlia.modules.rbac.backend.password.service;


import com.minlia.modules.rbac.backend.password.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.backend.password.body.ChangePasswordBySecurityCodeRequestBody;
import com.minlia.modules.rbac.backend.password.body.ResetPasswordRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserPasswordService {

    /**
     * 重置密码
     * @return
     */
    User resetPassword(ResetPasswordRequestBody body);

    /**
     * 根据原密码修改密码
     * @param body
     * @return
     */
    User changePassword(ChangePasswordByRawPasswordRequestBody body);

    /**
     * 根据验证码修改密码
     * @param body
     * @return
     */
    User changePassword(ChangePasswordBySecurityCodeRequestBody body);

}
