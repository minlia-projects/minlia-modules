package com.minlia.modules.rbac.openapi.registration.service;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.openapi.registration.body.UserAvailablitityRequestBody;
import com.minlia.modules.rbac.openapi.registration.body.UserRegistrationRequestBody;
import org.springframework.transaction.annotation.Transactional;


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
    StatefulBody availablitity(UserAvailablitityRequestBody body);

    /**
     * 用户绑定或注册
     * @param user
     * @return
     */
//    User bindOrRegistration(UserRegistrationRequestBody user);

}
