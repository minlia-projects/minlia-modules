package com.minlia.modules.rbac.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rbac.constant.RebaccaCode;
import com.minlia.modules.rbac.bean.to.RoleCTO;
import com.minlia.modules.rbac.bean.to.RoleUTO;
import com.minlia.modules.rbac.bean.domain.Role;
import com.minlia.modules.rbac.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        ApiAssert.state(!this.exists(body.getCode()), RebaccaCode.Message.ROLE_ALREADY_EXISTED);

        Role role = mapper.map(body,Role.class);
        roleMapper.create(role);
        if (CollectionUtils.isNotEmpty(body.getPermissions())) {
            roleMapper.grant(role.getId(), body.getPermissions());
        }
        return role;
    }

    @Override
    @Transactional
    public Role update(RoleUTO body) {
        ApiAssert.state(this.exists(body.getCode()), RebaccaCode.Message.ROLE_NOT_EXISTED);
        Role role = mapper.map(body,Role.class);
        roleMapper.update(role);
        return role;
    }

    @Override
    @Transactional
    public void delete(String code) {
        Role role = roleMapper.queryByCode(code);
        ApiAssert.notNull(role, RebaccaCode.Message.ROLE_NOT_EXISTED);
        //删除role、map_roles_permissions、map_users_roles
        roleMapper.delete(role.getId());
    }

    @Override
    @Transactional
    public void grantPermission(String code,List<Long> permissions) {
        Role role = roleMapper.queryByCode(code);
        ApiAssert.notNull(role, RebaccaCode.Message.ROLE_NOT_EXISTED);
        roleMapper.grant(role.getId(),permissions);
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
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> roleMapper.queryList());
    }

}
