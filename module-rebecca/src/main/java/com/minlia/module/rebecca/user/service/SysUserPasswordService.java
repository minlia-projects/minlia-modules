package com.minlia.module.rebecca.user.service;


import com.minlia.module.rebecca.user.bean.SysPasswordByCaptchaChangeTo;
import com.minlia.module.rebecca.user.bean.SysPasswordByRawPasswordChangeTo;
import com.minlia.module.rebecca.user.bean.SysPasswordResetTo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;

/**
 * @author garen
 */
public interface SysUserPasswordService {

    /**
     * 忘记密码
     */
    boolean forget(SysPasswordResetTo body);

    /**
     * 根据验证码修改密码
     *
     * @param body
     * @return
     */
    boolean change(SysPasswordByCaptchaChangeTo body);

    /**
     * 根据原密码修改密码
     *
     * @param body
     * @return
     */
    boolean change(SysPasswordByRawPasswordChangeTo body);

    /**
     * 修改用户密码
     *
     * @param entity
     * @param newPassword
     * @return
     */
    boolean change(SysUserEntity entity, String newPassword);
}
