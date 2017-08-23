package com.minlia.modules.rbac.v1.openapi.registration;


import com.minlia.modules.rbac.domain.User;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = false)
public interface UserRegistrationService {

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Transactional
    User registration(UserRegistrationRequestBody user);

    /**
     * 用户可用性验证
     * @param body
     * @return
     */
    Boolean availablitity(UserAvailablitityRequestBody body);


    /**
     * 用户绑定或注册
     *
     * @param user
     * @return
     */
    User bindOrRegistration(UserRegistrationRequestBody user);

}
