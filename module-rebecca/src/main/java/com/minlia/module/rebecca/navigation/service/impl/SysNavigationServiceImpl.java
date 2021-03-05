package com.minlia.module.rebecca.navigation.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.enumeration.TreeNodeTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.navigation.bean.SysNavigationSro;
import com.minlia.module.rebecca.navigation.bean.SysNavigationVo;
import com.minlia.module.rebecca.navigation.constant.SysNavigationCode;
import com.minlia.module.rebecca.navigation.entity.SysNavigationEntity;
import com.minlia.module.rebecca.navigation.mapper.SysNavigationMapper;
import com.minlia.module.rebecca.navigation.service.SysNavigationService;
import com.minlia.module.rebecca.role.bean.RoleTreeVo;
import com.minlia.module.rebecca.role.entity.SysRoleNavigationEntity;
import com.minlia.module.rebecca.role.entity.SysRoleUserEntity;
import com.minlia.module.rebecca.role.service.SysRoleNavigationService;
import com.minlia.module.rebecca.role.service.SysRoleService;
import com.minlia.module.rebecca.role.service.SysRoleUserService;
import com.minlia.module.rebecca.role.util.TreeUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 导航 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
@Service
public class SysNavigationServiceImpl extends ServiceImpl<SysNavigationMapper, SysNavigationEntity> implements SysNavigationService {

    private final SysRoleService sysRoleService;
    private final SysRoleUserService sysRoleUserService;
    private final SysRoleNavigationService sysRoleNavigationService;

    public SysNavigationServiceImpl(SysRoleService sysRoleService, SysRoleNavigationService sysRoleNavigationService, SysRoleUserService sysRoleUserService) {
        this.sysRoleService = sysRoleService;
        this.sysRoleUserService = sysRoleUserService;
        this.sysRoleNavigationService = sysRoleNavigationService;
    }

    @Override
    public SysNavigationEntity create(SysNavigationSro sro) {
        return save(sro);
    }

    @Override
    public SysNavigationEntity update(SysNavigationSro sro) {
        return save(sro);
    }

    @Override
    public Boolean delete(Long id) {
        SysNavigationEntity entity = this.getById(id);
        ApiAssert.notNull(entity, SysNavigationCode.Message.NOT_EXISTS);
        //当有子节点时报出异常
        ApiAssert.state(TreeNodeTypeEnum.LEAF.equals(entity.getType()), SysNavigationCode.Message.CAN_NOT_DELETE_HAS_CHILDREN);

        //检查父节点是否还有子节点, 如果无子节点时更新父节点为LEAF类型
        if (0 != entity.getParentId()) {
            long countChildren = this.count(Wrappers.<SysNavigationEntity>lambdaQuery().eq(SysNavigationEntity::getParentId, entity.getParentId()));
            //如果只有一个儿子
            if (countChildren == 1) {
                this.update(Wrappers.<SysNavigationEntity>lambdaUpdate()
                        .set(SysNavigationEntity::getType, TreeNodeTypeEnum.LEAF)
                        .eq(SysNavigationEntity::getId, entity.getParentId())
                );
            }
        }
        //移除角色-导航关系
        sysRoleNavigationService.remove(Wrappers.<SysRoleNavigationEntity>lambdaQuery().eq(SysRoleNavigationEntity::getNavigationId, id));
        return this.removeById(id);
    }

    @Override
    public Boolean hide(Long id) {
        SysNavigationEntity entity = this.getById(id);
        ApiAssert.notNull(entity, SysNavigationCode.Message.NOT_EXISTS);
        entity.setHideFlag(!entity.getHideFlag());
        this.updateById(entity);
        return entity.getHideFlag();
    }

    //    public static final String EXISTS_ROLE_TEMPLATE = "SELECT 1 FROM sys_role_navigation WHERE role_id = %s AND navigation_id = sys_navigation.id";
    public static final String EXISTS_ROLE_TEMPLATE = "SELECT 1 FROM sys_role_navigation WHERE role_id in (%s) AND navigation_id = sys_navigation.id";

    @Override
    public List<SysNavigationVo> getMe() {
        //TODO
//        String roleCode = MinliaSecurityContextHolder.getUserContext().getCurrrole();
//        SysRoleEntity roleEntity = sysRoleService.getOne(Wrappers.<SysRoleEntity>lambdaQuery().eq(SysRoleEntity::getCode, roleCode));
        List<Long> roleIds = sysRoleUserService.list(Wrappers.<SysRoleUserEntity>lambdaQuery().eq(SysRoleUserEntity::getUserId, SecurityContextHolder.getUid()))
                .stream().map(SysRoleUserEntity::getRoleId).collect(Collectors.toList());

        List<Long> navigationIds = sysRoleNavigationService.list(Wrappers.<SysRoleNavigationEntity>lambdaQuery().in(SysRoleNavigationEntity::getRoleId, roleIds))
                .stream().map(SysRoleNavigationEntity::getNavigationId).collect(Collectors.toList());

//        String existsRole = String.format(EXISTS_ROLE_TEMPLATE, roleEntity.getId());
        LambdaQueryWrapper<SysNavigationEntity> queryWrapper = Wrappers.<SysNavigationEntity>lambdaQuery()
                .eq(SysNavigationEntity::getParentId, 0)
                .eq(SysNavigationEntity::getHideFlag, false)
                .eq(SysNavigationEntity::getDisFlag, false)
                .in(SysNavigationEntity::getId, navigationIds)
//                .exists(existsRole)
                .orderByAsc(SysNavigationEntity::getSort);
        List<SysNavigationEntity> list = this.list(queryWrapper);
        this.setChildren(list, false, false, navigationIds);
        return DozerUtils.map(list, SysNavigationVo.class);
    }

    public void setChildren(List<SysNavigationEntity> list, Boolean hideFlag, Boolean disFlag, List<Long> navigationIds) {
        if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
            for (SysNavigationEntity entity : list) {
                if (TreeNodeTypeEnum.FOLDER.equals(entity.getType())) {
                    LambdaQueryWrapper<SysNavigationEntity> queryWrapper = new QueryWrapper<SysNavigationEntity>().lambda()
                            .eq(SysNavigationEntity::getParentId, entity.getId());
                    if (null != hideFlag) {
                        queryWrapper.eq(SysNavigationEntity::getHideFlag, hideFlag);
                    }
                    if (null != disFlag) {
                        queryWrapper.eq(SysNavigationEntity::getDisFlag, disFlag);
                    }
                    if (CollectionUtils.isNotEmpty(navigationIds)) {
                        queryWrapper.in(SysNavigationEntity::getId, navigationIds);
                    }
                    queryWrapper.orderByAsc(SysNavigationEntity::getSort);
                    List<SysNavigationEntity> children = this.list(queryWrapper);
                    entity.setChildren(children);
                    setChildren(children, hideFlag, disFlag, navigationIds);
                }
            }
        }
    }

    @Override
    public void setChildren(List<SysNavigationEntity> list, Boolean hideFlag, Boolean disFlag, String existsRole) {
        if (!org.springframework.util.CollectionUtils.isEmpty(list)) {
            for (SysNavigationEntity entity : list) {
                if (TreeNodeTypeEnum.FOLDER.equals(entity.getType())) {
                    LambdaQueryWrapper<SysNavigationEntity> queryWrapper = new QueryWrapper<SysNavigationEntity>().lambda()
                            .eq(SysNavigationEntity::getParentId, entity.getId());
                    if (null != hideFlag) {
                        queryWrapper.eq(SysNavigationEntity::getHideFlag, hideFlag);
                    }
                    if (null != disFlag) {
                        queryWrapper.eq(SysNavigationEntity::getDisFlag, disFlag);
                    }
                    if (StringUtils.isNotBlank(existsRole)) {
                        queryWrapper.exists(existsRole);
                    }
                    queryWrapper.orderByAsc(SysNavigationEntity::getSort);
                    List<SysNavigationEntity> children = this.list(queryWrapper);
                    entity.setChildren(children);
                    setChildren(children, hideFlag, disFlag, existsRole);
                }
            }
        }
    }

    @Override
    public List<RoleTreeVo> getTree() {
        List<RoleTreeVo> trees = this.list().stream()
                .filter(entity -> !entity.getId().equals(entity.getParentId()))
                .map(entity -> {
                    RoleTreeVo tree = new RoleTreeVo();
                    tree.setId(entity.getId());
                    tree.setName(entity.getName());
                    tree.setParentId(entity.getParentId());
                    return tree;
                }).collect(Collectors.toList());
        return TreeUtil.build(trees, 0L);
    }

    private SysNavigationEntity save(SysNavigationSro sro) {
        if (sro.getParentId() != 0) {
            SysNavigationEntity parent = this.getById(sro.getParentId());
            ApiAssert.notNull(parent, SysNavigationCode.Message.PARENT_NOT_EXISTS);
            //当创建子项时设置父类为FOLDER
            if (TreeNodeTypeEnum.LEAF.equals(parent.getType())) {
                this.update(Wrappers.<SysNavigationEntity>lambdaUpdate()
                        .eq(SysNavigationEntity::getId, parent.getId())
                        .set(SysNavigationEntity::getType, TreeNodeTypeEnum.FOLDER));
            }
        }
        SysNavigationEntity entity = DozerUtils.map(sro, SysNavigationEntity.class);
        this.saveOrUpdate(entity);
        return entity;
    }

}
