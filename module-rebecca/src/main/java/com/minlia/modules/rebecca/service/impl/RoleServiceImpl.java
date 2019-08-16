package com.minlia.modules.rebecca.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rebecca.bean.domain.Organization;
import com.minlia.modules.rebecca.bean.domain.Role;
import com.minlia.modules.rebecca.bean.dto.OrganizationTree;
import com.minlia.modules.rebecca.bean.dto.RoleTree;
import com.minlia.modules.rebecca.bean.dto.TreeUtil;
import com.minlia.modules.rebecca.bean.to.RoleCTO;
import com.minlia.modules.rebecca.bean.to.RoleUTO;
import com.minlia.modules.rebecca.constant.RoleCode;
import com.minlia.modules.rebecca.mapper.RoleMapper;
import com.minlia.modules.rebecca.service.PermissionService;
import com.minlia.modules.rebecca.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by will on 6/19/17.
 * 角色服务实现
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    @Override
    @Transactional
    public Role create(RoleCTO body) {
        ApiAssert.state(!this.exists(body.getCode()), RoleCode.Message.ALREADY_EXISTS);

        if (body.getParentId() != 0) {
            //判断角色是否存在
            ApiAssert.state(this.exists(body.getParentId()), RoleCode.Message.NOT_EXISTS);
        }

        Role role = mapper.map(body, Role.class);
        roleMapper.create(role);
        if (CollectionUtils.isNotEmpty(body.getPermissions())) {
            roleMapper.grant(role.getId(), body.getPermissions());
        }
        return role;
    }

    @Override
    @Transactional
    public Role update(RoleUTO body) {
        ApiAssert.state(this.exists(body.getCode()), RoleCode.Message.NOT_EXISTS);
        Role role = mapper.map(body, Role.class);
        roleMapper.update(role);
        return role;
    }

    @Override
    @Transactional
    public void delete(String code) {
        Role role = roleMapper.queryByCode(code);
        ApiAssert.notNull(role, RoleCode.Message.NOT_EXISTS);
        //删除role、map_roles_permissions、map_users_roles
        roleMapper.delete(role.getId());
    }

    @Override
    @Transactional
    public void grantPermission(String code, List<Long> permissions) {
        Role role = roleMapper.queryByCode(code);
        ApiAssert.notNull(role, RoleCode.Message.NOT_EXISTS);
        roleMapper.deleteRolePermission(role.getId());
        roleMapper.grant(role.getId(), permissions);
    }

    @Override
    public Boolean exists(Long id) {
        return null != roleMapper.queryById(id);
    }

    @Override
    public Boolean exists(String code) {
        return null != roleMapper.queryByCode(code);
    }

    @Override
    public Role queryById(Long id) {
        return roleMapper.queryById(id);
    }

    @Override
    public Role queryByCode(String code) {
        return roleMapper.queryByCode(code);
    }

    @Override
    public List<Role> queryByGuid(String guid) {
        return roleMapper.queryByGuid(guid);
    }

    @Override
    public List<String> queryCodeByUserId(Long userId) {
        return roleMapper.queryCodeByUserId(userId);
    }

    @Override
    public List<Long> queryIdByUserId(Long userId) {
        return roleMapper.queryIdByUserId(userId);
    }

    @Override
    public List<Role> queryList() {
        return roleMapper.queryList();
    }

    @Override
    public PageInfo<Role> queryPage(Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(() -> roleMapper.queryList());
    }

    @Override
    public boolean isParent(String parentCode, String childCode) {
        if (parentCode.equals(childCode)) return true;
        Role parentRole = queryByCode(parentCode);
        if (null == parentRole) return false;
        Role childRole = queryByCode(childCode);
        if (null == childRole) return false;
        return null == childRole.getParentId() ? false : parentRole.getId().equals(childRole.getParentId());
    }

    @Override
    public List<RoleTree> selectTree() {
        return buildTree(this.roleMapper.queryList(), 0L);
    }

    private List<RoleTree> buildTree(List<Role> roles, long root) {
        List<RoleTree> trees = roles.stream()
                .filter(role -> !role.getId().equals(role.getParentId()))
                .map(role -> {
                    RoleTree tree = new RoleTree();
                    tree.setId(role.getId());
                    tree.setCode(role.getCode());
                    tree.setName(role.getLabel());
                    tree.setParentId(role.getParentId());
//                    tree.setSort(role.getSort());
                    return tree;
                }).collect(Collectors.toList());
        return TreeUtil.build(trees, root);
    }

}
