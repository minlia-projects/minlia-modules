package com.minlia.module.rebecca.role.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.rebecca.role.bean.RoleTreeVo;
import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.minlia.module.rebecca.role.entity.SysRoleNavigationEntity;
import com.minlia.module.rebecca.role.entity.SysRolePermissionEntity;
import com.minlia.module.rebecca.role.entity.SysRoleUserEntity;
import com.minlia.module.rebecca.role.mapper.SysRoleMapper;
import com.minlia.module.rebecca.role.service.SysRoleNavigationService;
import com.minlia.module.rebecca.role.service.SysRolePermissionService;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.role.service.SysRoleUserService;
import com.minlia.module.rebecca.role.util.TreeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleEntity> implements SysRoleService {

    private final SysRoleUserService sysRoleUserService;
    private final SysRoleNavigationService sysRoleNavigationService;
    private final SysRolePermissionService sysRolePermissionService;

    public SysRoleServiceImpl(SysRoleUserService sysRoleUserService, SysRoleNavigationService sysRoleNavigationService, SysRolePermissionService sysRolePermissionService) {
        this.sysRoleUserService = sysRoleUserService;
        this.sysRoleNavigationService = sysRoleNavigationService;
        this.sysRolePermissionService = sysRolePermissionService;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeRoleById(Long id) {
        //移除角色-用户关系
        sysRoleUserService.remove(Wrappers.<SysRoleUserEntity>lambdaQuery().eq(SysRoleUserEntity::getRoleId, id));
        //移除角色-导航关系
        sysRoleNavigationService.remove(Wrappers.<SysRoleNavigationEntity>lambdaQuery().eq(SysRoleNavigationEntity::getRoleId, id));
        //移除角色-权限关系
        sysRolePermissionService.remove(Wrappers.<SysRolePermissionEntity>lambdaQuery().eq(SysRolePermissionEntity::getRoleId, id));
        //移除角色
        return this.removeById(id);
    }

    @Override
    public List<SysRoleEntity> getRolesByUserId(Long userId) {
        return null;
    }

    @Override
    public List<SysRoleEntity> getRolesByUserCode(Long uid) {
        return this.listByIds(this.getBaseMapper().selectIdsByUserId(uid));
    }

    @Override
    public List<String> getCodesByUserId(Long userId) {
        return this.getBaseMapper().selectCodesByUserId(userId);
    }

    @Override
    public List<RoleTreeVo> getTree() {
        return buildTree(this.list(), 0L);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantPermission(Long roleId, Set<Long> permissionIds) {
        //移除角色-权限关系
        sysRolePermissionService.remove(Wrappers.<SysRolePermissionEntity>lambdaQuery().eq(SysRolePermissionEntity::getRoleId, roleId));
        Set<SysRolePermissionEntity> list = permissionIds.stream().map(permissionId -> SysRolePermissionEntity.builder().roleId(roleId).permissionId(permissionId).build()).collect(Collectors.toSet());
        sysRolePermissionService.saveBatch(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void grantNavigation(Long roleId, Set<Long> navigationIds) {
        //移除角色-导航关系
        sysRoleNavigationService.remove(Wrappers.<SysRoleNavigationEntity>lambdaQuery().eq(SysRoleNavigationEntity::getRoleId, roleId));
        Set<SysRoleNavigationEntity> list = navigationIds.stream().map(navigationId -> SysRoleNavigationEntity.builder().roleId(roleId).navigationId(navigationId).build()).collect(Collectors.toSet());
        sysRoleNavigationService.saveBatch(list);
    }

    private List<RoleTreeVo> buildTree(List<SysRoleEntity> roles, long root) {
        List<RoleTreeVo> trees = roles.stream()
                .filter(role -> !role.getId().equals(role.getParentId()))
                .map(role -> {
                    RoleTreeVo tree = new RoleTreeVo();
                    tree.setId(role.getId());
                    tree.setCode(role.getCode());
                    tree.setName(role.getName());
                    tree.setParentId(role.getParentId());
                    return tree;
                }).collect(Collectors.toList());
        return TreeUtil.build(trees, root);
    }

}
