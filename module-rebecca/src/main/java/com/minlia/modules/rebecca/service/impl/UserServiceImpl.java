package com.minlia.modules.rebecca.service.impl;

import com.google.common.collect.Sets;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.constant.CommonCode;
import com.minlia.module.common.property.MinliaValidProperties;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.module.i18n.enumeration.LocaleEnum;
import com.minlia.module.riskcontrol.service.KieService;
import com.minlia.modules.rebecca.bean.domain.Role;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.bean.to.UserCTO;
import com.minlia.modules.rebecca.bean.to.UserUTO;
import com.minlia.modules.rebecca.constant.RoleCode;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.enumeration.UserStatusEnum;
import com.minlia.modules.rebecca.enumeration.UserUpdateTypeEcnum;
import com.minlia.modules.rebecca.event.CellphoneChangeEvent;
import com.minlia.modules.rebecca.event.RegistrationEvent;
import com.minlia.modules.rebecca.event.UserDeleteEvent;
import com.minlia.modules.rebecca.mapper.UserMapper;
import com.minlia.modules.rebecca.risk.event.ChangeCellphoneEvent;
import com.minlia.modules.rebecca.risk.event.ChangeEmailEvent;
import com.minlia.modules.rebecca.service.RoleService;
import com.minlia.modules.rebecca.service.UserHistoryService;
import com.minlia.modules.rebecca.service.UserQueryService;
import com.minlia.modules.rebecca.service.UserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;


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
    private CaptchaService captchaService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private UserHistoryService userHistoryService;

    @Autowired
    private MinliaValidProperties minliaValidProperties;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public User create(UserCTO cro) {
        User user = new User();

        //校验凭证是否有效
        if (StringUtils.isNotBlank(cro.getUsername())) {
            ApiAssert.state(!userQueryService.exists(UserQO.builder().username(cro.getUsername()).build()), UserCode.Message.USERNAME_ALREADY_EXISTS);
            user.setUsername(cro.getUsername());
        }
        if (StringUtils.isNotBlank(cro.getCellphone())) {
            ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(cro.getCellphone()).build()), UserCode.Message.CELLPHONE_ALREADY_EXISTS);
            user.setCellphone(cro.getCellphone());
        }
        if (StringUtils.isNotBlank(cro.getEmail())) {
            ApiAssert.state(!userQueryService.exists(UserQO.builder().email(cro.getEmail()).build()), UserCode.Message.EMAIL_ALREADY_EXISTS);
            user.setEmail(cro.getEmail());
        }

        //校验推荐人是否存在
        if (StringUtils.isNotEmpty(cro.getReferral())) {
            ApiAssert.state(userQueryService.exists(UserQO.builder().username(cro.getReferral()).build()), UserCode.Message.REFERRAL_NOT_EXISTS);
            user.setReferral(cro.getReferral());
        }

        //校验parentGuid是否存在
        if (StringUtils.isNotEmpty(cro.getParentGuid())) {
            ApiAssert.state(userQueryService.exists(UserQO.builder().guid(cro.getParentGuid()).build()), UserCode.Message.NOT_EXISTS);
            user.setParentGuid(cro.getParentGuid());
        }

        //校验角色是否存在
        Set<String> roles = cro.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            roles = Sets.newHashSet(cro.getDefaultRole());
        } else {
            roles.add(cro.getDefaultRole());
        }
        for (String roleCode : roles) {
            ApiAssert.state(roleService.exists(roleCode), RoleCode.Message.NOT_EXISTS);
        }

        //校验并查询默认角色
        Role role = roleService.queryByCode(cro.getDefaultRole());
        ApiAssert.notNull(role, RoleCode.Message.NOT_EXISTS);

        user.setGuid(SequenceUtils.nextval("guid").toString());
        user.setPassword(bCryptPasswordEncoder.encode(cro.getPassword()));
        user.setDefaultRole(role.getCode());
        user.setDefaultLocale(LocaleEnum.en_US);
        user.setNickname(cro.getNickname());
        user.setAccountEffectiveDate(null == cro.getAccountEffectiveDate() ? LocalDateTime.now().plusYears(1) : cro.getAccountEffectiveDate());
        user.setCredentialsEffectiveDate(null == cro.getCredentialsEffectiveDate() ? LocalDateTime.now().plusYears(1) : cro.getCredentialsEffectiveDate());
        user.setStatus(UserStatusEnum.ACTIVE);
        userMapper.create(user);

        //给用户授予角色
        this.grantWithUserWithRoleCodes(user, roles);

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEvent.onCompleted(user);
        return user;
    }

    @Override
    public User update(UserUTO uro, UserUpdateTypeEcnum userUpdateType) {
        User user = userQueryService.queryByGuidAndNotNull(uro.getGuid());

        mapper.map(uro, user);

        if (StringUtils.isNotBlank(uro.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(uro.getPassword()));
        }

        if (null != uro.getDefaultRole()) {
            Role role = roleService.queryByCode(uro.getDefaultRole());
            ApiAssert.notNull(role, UserCode.Message.DOES_NOT_HAD_ROLE);

            List<Long> roleIds = roleService.queryIdByUserId(user.getId());
            roleIds.add(role.getId());
            userMapper.grant(user.getId(), Sets.newHashSet(roleIds));
            user.setDefaultRole(role.getCode());
        }

        //校验parentGuid是否存在
        if (StringUtils.isNotEmpty(uro.getParentGuid())) {
            ApiAssert.state(userQueryService.exists(UserQO.builder().guid(uro.getParentGuid()).build()), UserCode.Message.NOT_EXISTS);
            user.setParentGuid(uro.getParentGuid());
        }

        this.update(user, userUpdateType);
        return user;
    }

    @Override
    public User update(User user, UserUpdateTypeEcnum userUpdateType) {
        userMapper.update(user);
        userHistoryService.insertSelective(user, userUpdateType);
        return user;
    }

    @Override
    public void changeCellphone(User user, String newCellphone, String captcha) {
        //正则校验
        ApiAssert.state(Pattern.matches(minliaValidProperties.getCellphone(), newCellphone), CommonCode.Message.CELLPHONE_FORMAT_ERROR);

        ChangeCellphoneEvent changeCellphoneEvent = new ChangeCellphoneEvent();
        changeCellphoneEvent.setScene("NUM_MOBILE_CHANGE_MTHS");
        changeCellphoneEvent.setSceneValue(newCellphone);
        changeCellphoneEvent.setGuid(user.getGuid());
        changeCellphoneEvent.setCount(userHistoryService.countByUpdateTypeAndGuidAndLastModifiedDateAfter(UserUpdateTypeEcnum.CHANGE_CELLPHONE, user.getGuid(), LocalDateTime.now().minusMonths(6)));
        KieService.execute(changeCellphoneEvent);

        //检查手机号码是否存在
        ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(newCellphone).build()), UserCode.Message.CELLPHONE_ALREADY_EXISTS);

        //校验验证码
        captchaService.validityByCellphone(newCellphone, captcha);

        user.setCellphone(newCellphone);
        this.update(user, UserUpdateTypeEcnum.CHANGE_CELLPHONE);
        CellphoneChangeEvent.onChange(user);
    }

    @Override
    public void changeEmail(User user, String newEmail, String captcha) {
        //正则校验
        ApiAssert.state(Pattern.matches(minliaValidProperties.getEmail(), newEmail), CommonCode.Message.EMAIL_FORMAT_ERROR);

        ChangeEmailEvent changeEmailEvent = new ChangeEmailEvent();
        changeEmailEvent.setScene("NUM_EMAIL_CHANGE_MTHS");
        changeEmailEvent.setSceneValue(newEmail);
        changeEmailEvent.setGuid(user.getGuid());
        changeEmailEvent.setCount(userHistoryService.countByUpdateTypeAndGuidAndLastModifiedDateAfter(UserUpdateTypeEcnum.CHANGE_EMAIL, user.getGuid(), LocalDateTime.now().minusMonths(6)));
        KieService.execute(changeEmailEvent);

        //检查邮箱是否存在
        ApiAssert.state(!userQueryService.exists(UserQO.builder().email(newEmail).build()), UserCode.Message.EMAIL_ALREADY_EXISTS);

        //校验验证码
        captchaService.validityByEmail(newEmail, captcha);

        user.setEmail(newEmail);
        this.update(user, UserUpdateTypeEcnum.CHANGE_EMAIL);
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
            user.setLockTime(LocalDateTime.now());
        } else {
            user.setLocked(true);
            user.setLockTime(LocalDateTime.now().plusMonths(1));
        }
        userMapper.update(user);
        return !user.getLocked();
    }

    @Override
    public Boolean disabled(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        if (UserStatusEnum.INACTIVE.equals(user.getStatus())) {
            user.setStatus(UserStatusEnum.ACTIVE);
        } else if (UserStatusEnum.ACTIVE.equals(user.getStatus())) {
            user.setStatus(UserStatusEnum.INACTIVE);
        } else {
            ApiAssert.state(false, UserCode.Message.ALREADY_TERMINATED);
        }
        userMapper.update(user);
        return UserStatusEnum.ACTIVE.equals(user.getStatus());
//        if (user.getEnabled()) {
//            user.setEnabled(false);
//        } else {
//            user.setEnabled(true);
//        }
//        userMapper.update(user);
//        return user.getEnabled();
    }

    @Override
    public void grant(String guid, Set<Long> roles) {
        User user = userMapper.queryOne(UserQO.builder().guid(guid).build());
        ApiAssert.notNull(user, UserCode.Message.NOT_EXISTS);

        boolean existRoleCode = false;
        String defaultRole = "GUEST";

        for (Long roleId : roles) {
            Role role = roleService.queryById(roleId);
            ApiAssert.notNull(role, RoleCode.Message.NOT_EXISTS);

            //判断是否存在默认角色
            if (!existRoleCode) {
                defaultRole = role.getCode();
                if (role.getCode().equals(user.getDefaultRole())) {
                    existRoleCode = true;
                }
            }
        }

        if (!existRoleCode) {
            user.setDefaultRole(defaultRole);
            userMapper.update(user);
        }

        this.grant(user.getId(), roles);
    }

    public void grantWithUserWithRoleCodes(User user, Set<String> roles) {
        boolean existRoleCode = false;
        String defaultRole = "GUEST";

        Set<Long> roleIds = Sets.newHashSet();

        for (String roleCode : roles) {
            Role role = roleService.queryByCode(roleCode);
            ApiAssert.notNull(role, RoleCode.Message.NOT_EXISTS);

            roleIds.add(role.getId());

            //判断是否存在默认角色
            if (!existRoleCode) {
                defaultRole = role.getCode();
                if (role.getCode().equals(user.getDefaultRole())) {
                    existRoleCode = true;
                }
            }
        }

        if (!existRoleCode) {
            user.setDefaultRole(defaultRole);
            userMapper.update(user);
        }

        this.grant(user.getId(), roleIds);
    }

    private void grant(Long userId, Set<Long> roles) {
        userMapper.deleteRole(userId);
        userMapper.grant(userId, roles);
    }

}
