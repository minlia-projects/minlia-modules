package com.minlia.modules.rbac.v1.backend.password;


import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.v1.backend.changempassword.body.ChangePasswordByRawPasswordRequestBody;
import com.minlia.modules.rbac.v1.backend.changempassword.body.ChangePasswordBySecurityCodeRequestBody;
import com.minlia.modules.rbac.v1.openapi.resetpassword.ResetPasswordRequestBody;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
public interface UserPasswordService {

    public static final String USER_CHANGE_PASSWORD="user.change.password";
    public static final String USER_RESET_PASSWORD="user.reset.password";


    /**
     * 重置密码
     * @return
     */
    User resetPassword(ResetPasswordRequestBody body);

    /**
     * 修改密码
     * @param body
     * @return
     */
    User changePassword(ChangePasswordByRawPasswordRequestBody body);


    User changePassword(ChangePasswordBySecurityCodeRequestBody body);

}
