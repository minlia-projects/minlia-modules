package com.minlia.module.rebecca.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.user.bean.SysUserCro;
import com.minlia.module.rebecca.user.bean.SysUserUro;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.enums.SysUserUpdateTypeEnum;
import org.springframework.transaction.annotation.Transactional;

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

    Boolean delete(Long id);

    Boolean lock(Long id);

    Boolean disable(Long id);

    Boolean addRole(Long id, String roleCode);

    Boolean grant(Long id, Set<Long> roles);

    Boolean grantByRoleCodes(Long id, Set<String> roleCodes);

    Boolean grant(SysUserEntity entity, Set<String> roleCodes);

    void addIntegral(Long uid, Long quantity);

}