package com.minlia.modules.rbac.v1.openapi.registration;

import com.google.common.collect.Sets;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;
import com.minlia.module.captcha.v1.service.SecureCodeService;
import com.minlia.modules.rbac.domain.Role;
import com.minlia.modules.rbac.domain.User;
import com.minlia.modules.rbac.event.RegistrationEventPublisher;
import com.minlia.modules.rbac.repository.RoleRepository;
import com.minlia.modules.rbac.repository.UserRepository;
import com.minlia.modules.rbac.service.UserReadOnlyService;
import com.minlia.modules.security.code.SecurityApiCode;
import com.minlia.modules.security.constant.SecurityConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserReadOnlyService userReadOnlyService;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    SecureCodeService secureCodeService;

    @Override
    public User registration(UserRegistrationRequestBody body) {
        return this.registration(body, SecureCodeSceneEnum.USER_REGISTRATION);
    }

    public User registration(UserRegistrationRequestBody body, SecureCodeSceneEnum type) {
        User user = new User();

        Boolean validity = secureCodeService.validity(body.getUsername(), body.getCode(), type);
        ApiPreconditions.not(validity, SecurityApiCode.INVALID_SECURE_CODE);
        //用户名
        user.setUsername(body.getUsername());
        //密码
        user.setPassword(bCryptPasswordEncoder.encode(body.getPassword()));
        //角色
        Role userRole = roleRepository.findOneByCode(SecurityConstant.ROLE_GUEST_CODE);
        user.setRoles(Sets.newHashSet(userRole));

        //用户可以登录相关开始
        user.setEnabled(Boolean.TRUE);
        user.setExpired(Boolean.FALSE);
        user.setCredentialsExpired(Boolean.FALSE);
        user.setLocked(Boolean.FALSE);
        //用户可以登录相关结束

        User userCreated = userRepository.save(user);

        //清除验证码
        secureCodeService.clean(body.getUsername(), body.getCode(), type);

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEventPublisher.onCompleted(userCreated.getId());

        return userCreated;
    }

    @Override
    public Boolean availablitity(UserAvailablitityRequestBody body) {
        User user = userReadOnlyService.findOneByUsernameOrEmailOrCellphone(body.getUsername());
        if (null != user) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User bindOrRegistration(UserRegistrationRequestBody body) {
        return this.registration(body, SecureCodeSceneEnum.BIND_OR_REGISTRATION);
    }
}
