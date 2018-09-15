package com.minlia.modules.rbac.backend.user.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
import com.minlia.modules.rbac.backend.role.entity.Role;
import com.minlia.modules.rbac.backend.role.service.RoleService;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.event.UserDeleteEvent;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
import com.minlia.modules.rbac.event.RegistrationEvent;
import com.minlia.modules.security.constant.SecurityConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;


@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User create(UserCreateRequestBody requestBody) {
        if (StringUtils.isNotEmpty(requestBody.getUsername())) {
            ApiAssert.state(!userQueryService.exists(UserQueryRequestBody.builder().username(requestBody.getUsername()).build()), RebaccaCode.Message.USERNAME_ALREADY_EXISTED);
        }
        if (StringUtils.isNotEmpty(requestBody.getCellphone())) {
            ApiAssert.state(!userQueryService.exists(UserQueryRequestBody.builder().username(requestBody.getCellphone()).build()), RebaccaCode.Message.USER_CELLPHONE_ALREADY_EXISTED);
        }
        if (StringUtils.isNotEmpty(requestBody.getEmail())) {
            ApiAssert.state(!userQueryService.exists(UserQueryRequestBody.builder().username(requestBody.getEmail()).build()), RebaccaCode.Message.USER_EMAIL_ALREADY_EXISTED);
        }
        if (StringUtils.isNotEmpty(requestBody.getReferral())) {
            ApiAssert.state(userQueryService.exists(UserQueryRequestBody.builder().username(requestBody.getReferral()).build()), RebaccaCode.Message.USER_REFERRAL_NOT_EXISTED);
        }

        //校验默认角色是否存在
        if (StringUtils.isNotBlank(requestBody.getDefaultRole())) {
            Role role = roleService.queryByCode(requestBody.getDefaultRole());
            ApiAssert.notNull(role,RebaccaCode.Message.ROLE_NOT_EXISTED);
        } else {
            //如果不传默认为用户
            requestBody.setDefaultRole(SecurityConstant.ROLE_USER_CODE);
        }

        User user = User.builder()
                .guid(SequenceUtils.nextval("guid").toString())
                .username(requestBody.getUsername())
                .cellphone(requestBody.getCellphone())
                .email(requestBody.getEmail())
                .password(bCryptPasswordEncoder.encode(requestBody.getPassword()))
                .defaultRole(requestBody.getDefaultRole())
                .referral(requestBody.getReferral())
                .build();
        userMapper.create(user);

        //授予角色
        if (CollectionUtils.isNotEmpty(requestBody.getRoles())) {
            this.grant(user.getId(),requestBody.getRoles());
        }

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEvent.onCompleted(user);
        return user;
    }

    @Override
    public User update(UserUpdateRequestBody body) {
        User user = userQueryService.queryByGuidAndNotNull(body.getGuid());
        if (StringUtils.isNotBlank(body.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(body.getPassword()));
        }
        if (StringUtils.isNotBlank(body.getDefaultRole())) {
            Role role = roleService.queryByCode(body.getDefaultRole());
            ApiAssert.notNull(role,RebaccaCode.Message.ROLE_NOT_EXISTED);
            user.setDefaultRole(body.getDefaultRole());
        }
        userMapper.update(user);
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public void delete(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        userMapper.delete(user.getId());
        //TODO 发布删除用户事件
        UserDeleteEvent.onDelete(user);
    }

    @Override
    public Boolean locked(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        if (user.getLocked()) {
            user.setLocked(false);
            user.setLockLimit(0);
            user.setLockTime(new Date());
        } else {
            user.setLocked(true);
            user.setLockTime(DateUtils.addMonths(new Date(),1));
        }
        userMapper.update(user);
        return !user.getLocked();
    }

    @Override
    public Boolean disabled(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        if (user.getEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userMapper.update(user);
        return !user.getLocked();
    }

    @Override
    public void grant(String guid, Set<Long> roles) {
        User user = userMapper.queryOne(UserQueryRequestBody.builder().guid(guid).build());
        ApiAssert.notNull(user,RebaccaCode.Message.USER_NOT_EXISTED);
        this.grant(user.getId(),roles);
    }

    @Override
    public void grant(long id, Set<Long> roles) {
        for (Long roleId : roles) {
            Role role = roleService.queryById(roleId);
            ApiAssert.notNull(role,RebaccaCode.Message.ROLE_NOT_EXISTED);
        }
        userMapper.grant(id,roles);
    }

}
