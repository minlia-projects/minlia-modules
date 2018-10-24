package com.minlia.modules.rbac.service;

import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.modules.rbac.constant.RebaccaCode;
import com.minlia.modules.rbac.bean.domain.Role;
import com.minlia.modules.rbac.bean.to.UserCTO;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.to.UserUTO;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.event.UserDeleteEvent;
import com.minlia.modules.rbac.mapper.UserMapper;
import com.minlia.modules.rbac.event.RegistrationEvent;
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
    public User create(UserCTO cto) {
        User user = new User();

        //校验凭证是否有效
        switch (cto.getMethod()) {
            case USERNAME:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().username(cto.getUsername()).build()), RebaccaCode.Message.USERNAME_ALREADY_EXISTED);
                user.setUsername(cto.getUsername());
                break;
            case CELLPHONE:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(cto.getCellphone()).build()), RebaccaCode.Message.USER_CELLPHONE_ALREADY_EXISTED);
                user.setCellphone(cto.getCellphone());
                break;
            case EMAIL:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().email(cto.getEmail()).build()), RebaccaCode.Message.USER_EMAIL_ALREADY_EXISTED);
                user.setEmail(cto.getEmail());
                break;
        }

        //校验推荐人是否存在
        if (StringUtils.isNotEmpty(cto.getReferral())) {
            ApiAssert.state(userQueryService.exists(UserQO.builder().username(cto.getReferral()).build()), RebaccaCode.Message.USER_REFERRAL_NOT_EXISTED);
            user.setReferral(cto.getReferral());
        }

        //校验角色是否存在
        for (Long roleId : cto.getRoles()) {
            ApiAssert.state(roleService.exists(roleId), RebaccaCode.Message.ROLE_NOT_EXISTED);
        }

        //校验并查询默认角色
        Role role = roleService.queryById(cto.getDefaultRole());
        ApiAssert.notNull(role,RebaccaCode.Message.ROLE_NOT_EXISTED);

        user.setGuid(SequenceUtils.nextval("guid").toString());
        user.setPassword(bCryptPasswordEncoder.encode(cto.getPassword()));
        user.setDefaultRole(role.getCode());
        userMapper.create(user);

        //给用户授予角色
        cto.getRoles().add(cto.getDefaultRole());
        if (CollectionUtils.isNotEmpty(cto.getRoles())) {
            this.grant(user.getId(),cto.getRoles());
        }

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEvent.onCompleted(user);
        return user;
    }

    @Override
    public User update(UserUTO body) {
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
        User user = userMapper.queryOne(UserQO.builder().guid(guid).build());
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
