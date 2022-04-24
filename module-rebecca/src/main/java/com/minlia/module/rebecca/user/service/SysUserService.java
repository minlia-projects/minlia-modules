package com.minlia.module.rebecca.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.bean.SysUserUro;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;

import java.util.Set;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-24
 */
public interface SysUserService extends IService<SysUserEntity> {

    SysUserEntity create(SysUserCro cro);

    SysUserEntity update(SysUserUro uro, SysUserUpdateTypeEnum sysUserUpdateType);

    SysUserEntity update(SysUserEntity entity, SysUserUpdateTypeEnum sysUserUpdateType);

    void changeCellphone(SysUserEntity entity, String newCellphone, String vcode);

    void changeEmail(SysUserEntity entity, String newEmail, String vcode);

    boolean delete(Long id);

    boolean lockToggle(Long id);

    boolean lock(Long id, long seconds, String remark);

    boolean disable(Long id, String remark);

    boolean disableToggle(Long id);

    boolean addRole(Long id, String roleCode);

    boolean grant(Long id, Set<Long> roles);

    boolean grantByRoleCodes(Long id, Set<String> roleCodes);

    boolean grant(SysUserEntity entity, Set<String> roleCodes);

}