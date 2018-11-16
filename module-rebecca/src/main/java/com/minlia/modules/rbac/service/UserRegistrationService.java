package com.minlia.modules.rbac.service;


import com.minlia.cloud.body.Response;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.to.UserAvailablitityTO;
import com.minlia.modules.rbac.bean.to.UserRegistrationTO;


public interface UserRegistrationService {

    /**
     * 用户注册
     * @param to
     * @return
     */
    User registration(UserRegistrationTO to);

    /**
     * 用户可用性验证
     * @param to
     * @return
     */
    Response availablitity(UserAvailablitityTO to);

}
