package com.minlia.module.rebecca.user.service;


import com.minlia.cloud.body.Response;
import com.minlia.module.rebecca.user.bean.UserAvailablitityTo;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import org.springframework.transaction.annotation.Transactional;


/**
 * @author garen
 */
public interface SysUserRegisterService {

    /**
     * 用户注册
     *
     * @param to
     * @return
     */
    Response<SysUserEntity> register(UserRegisterRo to);

    Response<SysUserEntity> registerByCellphone(UserRegisterRo ro);

    /**
     * 用户可用性验证
     *
     * @param to
     * @return
     */
    Response availablitity(UserAvailablitityTo to);

}
