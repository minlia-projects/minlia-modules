package com.minlia.modules.rbac.backend.user.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.modules.rbac.backend.common.constant.SecurityApiCode;
import com.minlia.modules.rbac.backend.role.entity.Role;
import com.minlia.modules.rbac.backend.role.service.RoleService;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserGarenRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.event.UserDeleteEvent;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
import com.minlia.modules.rbac.event.RegistrationEvent;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.dozer.Mapper;
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
    private Mapper mapper;
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
        ApiPreconditions.is(userQueryService.exists(requestBody.getUsername()), ApiCode.NOT_NULL,"用户名已被使用");

        //验证推荐人
        if (StringUtils.isNotEmpty(requestBody.getReferral())) {
            User user = userMapper.queryByGuid(requestBody.getReferral());
            ApiPreconditions.is(null == user, SecurityApiCode.USER_REFERRAL_NOT_FOUND,"推荐人不存在");
        }

        //校验默认角色是否存在
        if (StringUtils.isNotBlank(requestBody.getDefaultRole())) {
            Role role = roleService.queryByCode(requestBody.getDefaultRole());
            if (null == role) {
                ApiPreconditions.is(true,SecurityApiCode.ROLE_NOT_EXISTED);
            }
        }

        User user = User.builder()
                .guid(SequenceUtils.nextval("guid").toString())
                .username(requestBody.getUsername())
                .cellphone(requestBody.getCellphone())
                .email(requestBody.getEmail())
                .password(bCryptPasswordEncoder.encode(requestBody.getPassword()))
                .defaultRole(requestBody.getDefaultRole())
                .referral(requestBody.getReferral())
                .enabled(Boolean.TRUE)
                .credentialsExpired(Boolean.FALSE)
                .locked(Boolean.FALSE)
                .lockLimit(NumberUtils.INTEGER_ZERO)
                .enabled(Boolean.FALSE)
                .build();
        userMapper.create(user);

        //授予角色
        if (CollectionUtils.isNotEmpty(requestBody.getRoles())) {
            this.grant(user.getId(),requestBody.getRoles());
        }

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEvent.onCompleted(user.getId());
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
            if (null == role) {
                ApiPreconditions.is(true,SecurityApiCode.ROLE_NOT_EXISTED);
            }
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
//            user.setLockTime(user.getLockTime().plusMonths(1));
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
        User user = userMapper.queryByGuid(guid);
        ApiPreconditions.is(null == user,SecurityApiCode.USER_NOT_EXISTED,"用户不存在："+user.getGuid());
        this.grant(user.getId(),roles);
    }

    @Override
    public void grant(long id, Set<Long> roles) {
        for (Long roleId : roles) {
            Role role = roleService.queryById(roleId);
            ApiPreconditions.is(null == role,SecurityApiCode.ROLE_NOT_EXISTED,"角色不存在："+roleId);
        }
        userMapper.grant(id,roles);
    }

}
