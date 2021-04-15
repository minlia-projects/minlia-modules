package com.minlia.module.rebecca.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Sets;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.hsjs.integral.bean.HsjsIntegralPlusData;
import com.minlia.hsjs.integral.event.HsjsIntegralPlusEvent;
import com.minlia.module.captcha.service.CaptchaService;
import com.minlia.module.common.constant.CommonCode;
import com.minlia.module.common.property.MinliaValidProperties;
import com.minlia.module.common.util.NumberGenerator;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.role.constant.SysRoleCode;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.entity.SysRoleUserEntity;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.role.service.SysRoleUserService;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.bean.SysUserUro;
import com.minlia.module.rebecca.user.constant.SysUserCode;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.entity.SysUserHistoryEntity;
import com.minlia.module.rebecca.user.enums.SysUserStatusEnum;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import com.minlia.module.rebecca.user.event.SysCellphoneChangeEvent;
import com.minlia.module.rebecca.user.event.SysEmailChangeEvent;
import com.minlia.module.rebecca.user.event.SysUserCreateEvent;
import com.minlia.module.rebecca.user.event.SysUserDeleteEvent;
import com.minlia.module.rebecca.user.mapper.SysUserMapper;
import com.minlia.module.rebecca.user.service.SysUserHistoryService;
import com.minlia.module.rebecca.user.service.SysUserRelationService;
import com.minlia.module.rebecca.user.service.SysUserService;
import com.minlia.modules.security.config.SysSecurityConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-24
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserEntity> implements SysUserService {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private CaptchaService captchaService;
    @Autowired
    private SysSecurityConfig sysSecurityConfig;
    @Autowired
    private SysRoleUserService sysRoleUserService;
    @Autowired
    private SysUserHistoryService sysUserHistoryService;
    @Autowired
    private MinliaValidProperties minliaValidProperties;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SysUserRelationService sysUserRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserEntity create(SysUserCro cro) {
        SysUserEntity entity = new SysUserEntity();

        //邀请码验证
        SysUserEntity parent = null;
        if (Objects.nonNull(cro.getInviteCode())) {
            parent = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getInviteCode, cro.getInviteCode()).eq(SysUserEntity::getDisFlag, false));
            ApiAssert.notNull(parent, SysUserCode.Message.INVITE_CODE_NOT_EXISTS);
        }
        //校验凭证是否有效
        if (StringUtils.isNotBlank(cro.getUsername())) {
            ApiAssert.state(this.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getUsername, cro.getUsername())) == 0, SysUserCode.Message.USERNAME_ALREADY_EXISTS);
            entity.setUsername(cro.getUsername());
        } else if (StringUtils.isNotBlank(cro.getCellphone())) {
            ApiAssert.state(this.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getCellphone, cro.getCellphone())) == 0, SysUserCode.Message.CELLPHONE_ALREADY_EXISTS);
            entity.setCellphone(cro.getCellphone());
            entity.setUsername("mid_" + RandomStringUtils.randomAlphanumeric(16));
        } else if (StringUtils.isNotBlank(cro.getEmail())) {
            ApiAssert.state(this.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getEmail, cro.getEmail())) == 0, SysUserCode.Message.EMAIL_ALREADY_EXISTS);
            entity.setEmail(cro.getEmail());
            entity.setUsername("mid_" + RandomStringUtils.randomAlphanumeric(16));
        }

        //校验角色是否存在
        Set<String> roles = cro.getRoles();
        if (CollectionUtils.isEmpty(roles)) {
            roles = Sets.newHashSet(cro.getDefaultRole());
        } else {
            roles.add(cro.getDefaultRole());
        }
        LambdaQueryWrapper<SysRoleEntity> queryWrapper = new QueryWrapper<SysRoleEntity>().lambda().in(SysRoleEntity::getCode, roles);
        ApiAssert.state(sysRoleService.count(queryWrapper) == roles.size(), SysRoleCode.Message.NOT_EXISTS);

        if (StringUtils.isEmpty(cro.getNickname())) {
            entity.setNickname(sysSecurityConfig.getNicknamePrefix() + RandomStringUtils.randomAlphanumeric(10));
        } else {
            entity.setNickname(cro.getNickname());
        }

        entity.setOrgId(cro.getOrgId());
        entity.setPassword(bCryptPasswordEncoder.encode(cro.getPassword()));
        entity.setInviteCode(NumberGenerator.generatorAlphanumeric(10));
        entity.setDefaultRole(cro.getDefaultRole());
        entity.setDefaultLocale(cro.getDefaultLocale());
        entity.setAccountEffectiveDate(null == cro.getAccountEffectiveDate() ? LocalDateTime.now().plusDays(sysSecurityConfig.getAccountEffectiveDays()) : cro.getAccountEffectiveDate());
        entity.setCredentialsEffectiveDate(null == cro.getCredentialsEffectiveDate() ? LocalDateTime.now().plusDays(sysSecurityConfig.getCredentialsEffectiveDays()) : cro.getCredentialsEffectiveDate());
        entity.setStatus(SysUserStatusEnum.ACTIVE);
        this.save(entity);

        //给用户授予角色
        this.grant(entity, roles);

        //设置邀请关系
        if (Objects.nonNull(parent)) {
            sysUserRelationService.create(parent.getId(), entity.getId());
            HsjsIntegralPlusEvent.publish(HsjsIntegralPlusData.builder().uid(parent.getId()).businessId(parent.getId()).businessType("INVITATION_REGISTER").build());
        }

        //调用事件发布器, 发布系统用户系统注册完成事件, 由业务系统接收到此事件后进行相关业务操作
        SysUserCreateEvent.onCompleted(entity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserEntity update(SysUserUro uro, SysUserUpdateTypeEnum sysUserUpdateType) {
        SysUserEntity entity = DozerUtils.map(uro, SysUserEntity.class);

        if (StringUtils.isNotBlank(uro.getPassword())) {
            entity.setPassword(bCryptPasswordEncoder.encode(uro.getPassword()));
            entity.setCredentialsEffectiveDate(LocalDateTime.now().plusDays(sysSecurityConfig.getCredentialsEffectiveDays()));
        }

        if (null != uro.getDefaultRole()) {
            //判断角色是否存在
            SysRoleEntity sysRoleEntity = sysRoleService.getOne(new QueryWrapper<SysRoleEntity>().lambda().eq(SysRoleEntity::getCode, uro.getDefaultRole()));
            ApiAssert.notNull(sysRoleEntity, SysUserCode.Message.DOES_NOT_HAD_ROLE);

            //获取原有角色
            List<SysRoleUserEntity> roleUsers = sysRoleUserService.list(new QueryWrapper<SysRoleUserEntity>().lambda().select(SysRoleUserEntity::getRoleId).eq(SysRoleUserEntity::getUserId, entity.getId()));
            Set<Long> roleIds = roleUsers.stream().map(roleUser -> roleUser.getRoleId()).collect(Collectors.toSet());
            roleIds.add(sysRoleEntity.getId());
            this.grant(entity.getId(), Sets.newHashSet(roleIds));
        }

        this.update(entity, sysUserUpdateType);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysUserEntity update(SysUserEntity entity, SysUserUpdateTypeEnum sysUserUpdateType) {
        this.saveOrUpdate(entity);
        SysUserHistoryEntity sysUserHistoryEntity = DozerUtils.map(entity, SysUserHistoryEntity.class);
        sysUserHistoryEntity.setId(null);
        sysUserHistoryEntity.setUpdateType(sysUserUpdateType);
        sysUserHistoryService.save(sysUserHistoryEntity);
        return entity;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeCellphone(SysUserEntity entity, String newCellphone, String vcode) {
        //正则校验
        ApiAssert.state(Pattern.matches(minliaValidProperties.getCellphone(), newCellphone), CommonCode.Message.CELLPHONE_FORMAT_ERROR);

        //检查手机号码是否存在
        ApiAssert.state(this.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getCellphone, newCellphone)) == 0, SysUserCode.Message.CELLPHONE_ALREADY_EXISTS);

        //校验验证码
        captchaService.validity(newCellphone, vcode);
        entity.setCellphone(newCellphone);
        this.update(entity, SysUserUpdateTypeEnum.CHANGE_CELLPHONE);
        SysCellphoneChangeEvent.onChange(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeEmail(SysUserEntity entity, String newEmail, String vcode) {
        //正则校验
        ApiAssert.state(Pattern.matches(minliaValidProperties.getEmail(), newEmail), CommonCode.Message.EMAIL_FORMAT_ERROR);

        //检查邮箱是否存在
        ApiAssert.state(this.count(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getEmail, newEmail)) == 0, SysUserCode.Message.EMAIL_ALREADY_EXISTS);

        //校验验证码
        captchaService.validity(newEmail, vcode);

        entity.setEmail(newEmail);
        this.update(entity, SysUserUpdateTypeEnum.CHANGE_EMAIL);
        SysEmailChangeEvent.onChange(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(Long id) {
        SysUserEntity entity = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, id));
        boolean result = this.removeById(entity.getId());
        SysUserDeleteEvent.onDelete(entity);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean lock(Long id) {
        SysUserEntity entity = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, id));
        if (entity.getLockFlag()) {
            entity.setLockFlag(false);
            entity.setLockLimit(0);
            entity.setLockTime(LocalDateTime.now());
        } else {
            entity.setLockFlag(true);
            entity.setLockTime(LocalDateTime.now().plusDays(sysSecurityConfig.getLockedDays()));
        }
        this.update(entity, SysUserUpdateTypeEnum.SYSTEM_UPDATE);
        return entity.getLockFlag();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean disable(Long id) {
        SysUserEntity entity = this.getOne(Wrappers.<SysUserEntity>lambdaQuery().eq(SysUserEntity::getId, id));
        entity.setDisFlag(!entity.getDisFlag());
        this.update(entity, SysUserUpdateTypeEnum.SYSTEM_UPDATE);
        return entity.getDisFlag();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean addRole(Long id, String roleCode) {
        SysRoleEntity sysRoleEntity = sysRoleService.getOne(new QueryWrapper<SysRoleEntity>().lambda().select(SysRoleEntity::getId).eq(SysRoleEntity::getCode, roleCode));
        return sysRoleUserService.save(SysRoleUserEntity.builder().roleId(sysRoleEntity.getId()).userId(id).build());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean grant(Long id, Set<Long> roleIds) {
        //移除用户-角色关系
        sysRoleUserService.remove(Wrappers.<SysRoleUserEntity>lambdaUpdate().eq(SysRoleUserEntity::getUserId, id));
        //批量保存
        List<SysRoleUserEntity> sysRoleUserEntities = roleIds.stream().map(roleId -> SysRoleUserEntity.builder().roleId(roleId).userId(id).build()).collect(Collectors.toList());
        return sysRoleUserService.saveBatch(sysRoleUserEntities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean grantByRoleCodes(Long id, Set<String> roleCodes) {
        List<SysRoleEntity> roles = sysRoleService.list(new QueryWrapper<SysRoleEntity>().lambda().select(SysRoleEntity::getId).in(SysRoleEntity::getCode, roleCodes));
        Set<Long> roleIds = roles.stream().map(role -> role.getId()).collect(Collectors.toSet());
        return this.grant(id, roleIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean grant(SysUserEntity entity, Set<String> roleCodes) {
        return this.grantByRoleCodes(entity.getId(), roleCodes);
    }

}