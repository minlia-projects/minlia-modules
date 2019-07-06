package com.minlia.modules.rebecca.service.impl;

import com.google.common.collect.Sets;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.constant.CommonCode;
import com.minlia.module.common.property.MinliaValidProperties;
import com.minlia.module.data.util.SequenceUtils;
import com.minlia.module.drools.service.ReloadDroolsRulesService;
import com.minlia.module.riskcontrol.service.RiskRecordService;
import com.minlia.modules.rebecca.bean.domain.Role;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.bean.to.UserCTO;
import com.minlia.modules.rebecca.bean.to.UserUTO;
import com.minlia.modules.rebecca.constant.RoleCode;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.context.SecurityContextHolder;
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
import org.apache.commons.lang3.time.DateUtils;
import org.kie.api.runtime.StatelessKieSession;
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
    private UserMapper userMapper;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CaptchaService captchaService;

    @Autowired
    private UserQueryService userQueryService;

    @Autowired
    private RiskRecordService riskRecordService;

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
        switch (cro.getMethod()) {
            case USERNAME:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().username(cro.getUsername()).build()), UserCode.Message.USERNAME_ALREADY_EXISTS);
                user.setUsername(cro.getUsername());
                break;
            case CELLPHONE:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().cellphone(cro.getCellphone()).build()), UserCode.Message.CELLPHONE_ALREADY_EXISTS);
                user.setCellphone(cro.getCellphone());
                break;
            case EMAIL:
                ApiAssert.state(!userQueryService.exists(UserQO.builder().email(cro.getEmail()).build()), UserCode.Message.EMAIL_ALREADY_EXISTS);
                user.setEmail(cro.getEmail());
                break;
        }

        //校验推荐人是否存在
        if (StringUtils.isNotEmpty(cro.getReferral())) {
            ApiAssert.state(userQueryService.exists(UserQO.builder().username(cro.getReferral()).build()), UserCode.Message.REFERRAL_NOT_EXISTS);
            user.setReferral(cro.getReferral());
        }

        //校验角色是否存在
        Set<Long> roles = cro.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            roles = Sets.newHashSet(cro.getDefaultRole());
        } else {
            roles.add(cro.getDefaultRole());
        }
        for (Long roleId : roles) {
            ApiAssert.state(roleService.exists(roleId), RoleCode.Message.NOT_EXISTS);
        }

        //校验并查询默认角色
        Role role = roleService.queryById(cro.getDefaultRole());
        ApiAssert.notNull(role, RoleCode.Message.NOT_EXISTS);

        user.setGuid(SequenceUtils.nextval("guid").toString());
        user.setPassword(bCryptPasswordEncoder.encode(cro.getPassword()));
        user.setDefaultRole(role.getCode());
        user.setNickname(cro.getNickname());
        userMapper.create(user);

        //给用户授予角色
        this.grant(user.getId(), roles);

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        RegistrationEvent.onCompleted(user);
        return user;
    }

    @Override
    public User update(UserUTO uro, UserUpdateTypeEcnum userUpdateType) {
        User user = userQueryService.queryByGuidAndNotNull(uro.getGuid());
        if (StringUtils.isNotBlank(uro.getPassword())) {
            user.setPassword(bCryptPasswordEncoder.encode(uro.getPassword()));
        }
        if (null != uro.getDefaultRole()) {
            Role role = roleService.queryById(uro.getDefaultRole());
            ApiAssert.notNull(role, UserCode.Message.DOES_NOT_HAD_ROLE);

            List<Long> roleIds = roleService.queryIdByUserId(user.getId());
            roleIds.add(role.getId());
            userMapper.grant(user.getId(), Sets.newHashSet(roleIds));
            user.setDefaultRole(role.getCode());
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

        StatelessKieSession kieSession = ReloadDroolsRulesService.kieContainer.newStatelessKieSession();
        kieSession.setGlobal("userHistoryService", userHistoryService);
        kieSession.setGlobal("riskEventListService", riskRecordService);
        ChangeCellphoneEvent changeCellphoneEvent = new ChangeCellphoneEvent();
        changeCellphoneEvent.setEventId("asdfsd");
        changeCellphoneEvent.setScene("change_cellphone");
        changeCellphoneEvent.setGuid(user.getGuid());
        changeCellphoneEvent.setCellphone(newCellphone);
        changeCellphoneEvent.setOperateTime(LocalDateTime.now());
        kieSession.execute(changeCellphoneEvent);

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

        StatelessKieSession kieSession = ReloadDroolsRulesService.kieContainer.newStatelessKieSession();
        kieSession.setGlobal("userHistoryService", userHistoryService);
        kieSession.setGlobal("riskEventListService", riskRecordService);
        ChangeEmailEvent changeEmailEvent = new ChangeEmailEvent();
        changeEmailEvent.setEventId("asdfsd");
        changeEmailEvent.setScene("change_email");
        changeEmailEvent.setGuid(user.getGuid());
        changeEmailEvent.setEmail(newEmail);
        changeEmailEvent.setOperateTime(LocalDateTime.now());
        kieSession.execute(changeEmailEvent);

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
        if (user.getEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userMapper.update(user);
        return user.getEnabled();
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

    private void grant(Long userId, Set<Long> roles) {
        userMapper.deleteRole(userId);
        userMapper.grant(userId, roles);
    }

}
