package com.minlia.modules.rbac.service;


import com.minlia.cloud.body.Response;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.bean.to.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.bean.to.UserRegistrationRequestBody;


public interface UserRegistrationService {

    /**
     * 用户注册
     * @param user
     * @return
     */
    User registration(UserRegistrationRequestBody user);

    /**
     * 用户可用性验证
     * @param body
     * @return
     */
    Response availablitity(UserAvailablitityRequestBody body);

}
