package com.minlia.modules.rbac.service;

import com.google.common.collect.Sets;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.modules.rbac.bean.domain.Role;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.bean.qo.UserQO;
import com.minlia.modules.rbac.bean.to.UserCTO;
import com.minlia.modules.rbac.bean.to.UserUTO;
import com.minlia.modules.rbac.constant.RebaccaCode;
import com.minlia.modules.rbac.event.RegistrationEvent;
import com.minlia.modules.rbac.event.UserDeleteEvent;
import com.minlia.modules.rbac.mapper.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
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
    public User create(UserCTO cro) {
        User user = new User();

        //校验凭证是否有效
        switch (cro.getMethod()) {
            case USERNAME:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().username(cro.getUsername()).build()), RebaccaCode.Message.USERNAME_ALREADY_EXISTED);
                user.setUsername(cro.getUsername());
                break;
            case CELLPHONE:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(cro.getCellphone()).build()), RebaccaCode.Message.USER_CELLPHONE_ALREADY_EXISTED);
                user.setCellphone(cro.getCellphone());
                break;
            case EMAIL:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().email(cro.getEmail()).build()), RebaccaCode.Message.USER_EMAIL_ALREADY_EXISTED);
                user.setEmail(cro.getEmail());
                break;
        }

        //校验推荐人是否存在
        if (StringUtils.isNotEmpty(cro.getReferral())) {
            ApiAssert.state(userQueryService.exists(UserQO.builder().username(cro.getReferral()).build()), RebaccaCode.Message.USER_REFERRAL_NOT_EXISTED);
            user.setReferral(cro.getReferral());
        }

        //校验角色是否存在
        for (Long roleId : cro.getRoles()) {
            ApiAssert.state(roleService.exists(roleId), RebaccaCode.Message.ROLE_NOT_EXISTED);
        }

        //校验并查询默认角色
        Role role = roleService.queryById(cro.getDefaultRole());
        ApiAssert.notNull(role,RebaccaCode.Message.ROLE_NOT_EXISTED);

        user.setGuid(SequenceUtils.nextval("guid").toString());
        user.setPassword(bCryptPasswordEncoder.encode(cro.getPassword()));
        user.setDefaultRole(role.getCode());
        user.setNickname(cro.getNickname());
        userMapper.create(user);

        //给用户授予角色
        cro.getRoles().add(cro.getDefaultRole());
        if (CollectionUtils.isNotEmpty(cro.getRoles())) {
            userMapper.grant(user.getId(), cro.getRoles());
        }

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEvent.onCompleted(user);
        return user;
    }

    @Override
    public User update(UserUTO uro) {
        User user = userQueryService.queryByGuidAndNotNull(uro.getGuid());
        if (StringUtils.isNotBlank(uro.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(uro.getPassword()));
        }
        if (null !=uro.getDefaultRole()) {
            Role role = roleService.queryById(uro.getDefaultRole());
            ApiAssert.notNull(role, RebaccaCode.Message.USER_DOES_NOT_HAD_ROLE);

            List<Long> roleIds = roleService.queryIdByUserId(user.getId());
            roleIds.add(role.getId());
            userMapper.grant(user.getId(), Sets.newHashSet(roleIds));

            user.setDefaultRole(role.getCode());
        }
//        if (StringUtils.isNotBlank(body.getDefaultRole())) {
//            Role role = roleService.queryByCode(body.getDefaultRole());
//            ApiAssert.notNull(role,CommonCode.Message.ROLE_NOT_EXISTED);
//            user.setDefaultRole(body.getDefaultRole());
//        }
        userMapper.update(user);
        return user;
    }

    @Override
    public User update(User user) {
        userMapper.update(user);
        return user;
    }

    @Override
    public int delete(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        //TODO 发布删除用户事件
        UserDeleteEvent.onDelete(user);
        return userMapper.delete(user.getId());
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

        for (Long roleId : roles) {
            Role role = roleService.queryById(roleId);
            ApiAssert.notNull(role,RebaccaCode.Message.ROLE_NOT_EXISTED);
        }
        userMapper.grant(user.getId(),roles);
    }

}
