package com.minlia.modules.rbac.service;


import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.to.PasswordByCaptchaChangeTO;
import com.minlia.modules.rbac.bean.to.PasswordByRawPasswordChangeTO;
import com.minlia.modules.rbac.bean.to.PasswordResetTO;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserPasswordService {

    /**
     * 忘记密码
     * @return
     */
    User forget(PasswordResetTO body);

    /**
     * 根据验证码修改密码
     * @param body
     * @return
     */
    User change(PasswordByCaptchaChangeTO body);

    /**
     * 根据原密码修改密码
     * @param body
     * @return
     */
    User change(PasswordByRawPasswordChangeTO body);

}
