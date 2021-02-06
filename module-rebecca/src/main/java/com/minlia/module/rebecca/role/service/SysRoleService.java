package com.minlia.module.rebecca.role.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.role.bean.RoleTreeVo;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
public interface SysRoleService extends IService<SysRoleEntity> {

    /**
     * 通过角色ID，删除角色，并清空角色-用户、角色-导航、角色-权限点缓存
     *
     * @param id
     * @return
     */
    Boolean removeRoleById(Long id);

    /**
     * 通过用户ID，查询角色信息
     *
     * @param userId
     * @return
     */
    List<SysRoleEntity> getRolesByUserId(Long userId);

    List<String> getCodesByUserId(Long userId);

    /**
     * 查询组织树菜单
     *
     * @return
     */
    List<RoleTreeVo> getTree();

    void grantPermission(Long roleId, Set<Long> permissionIds);

    void grantNavigation(Long roleId, Set<Long> navigationIds);

}
