package com.minlia.modules.rbac.backend.role.service;

import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.backend.common.constant.SecurityApiCode;
import com.minlia.modules.rbac.backend.permission.service.PermissionService;
import com.minlia.modules.rbac.backend.role.body.RoleCreateRequestBody;
import com.minlia.modules.rbac.backend.role.body.RoleUpdateRequestBody;
import com.minlia.modules.rbac.backend.role.entity.Role;
import com.minlia.modules.rbac.backend.role.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public Role create(RoleCreateRequestBody body) {
        ApiPreconditions.is(this.exists(body.getCode()), SecurityApiCode.ROLE_ALREADY_EXISTED,"角色已存在");

        Role role = mapper.map(body,Role.class);
        roleMapper.create(role);

        roleMapper.grant(role.getId(),body.getPermissions());
        return role;
    }

    @Override
    public Role update(RoleUpdateRequestBody body) {
        ApiPreconditions.not(this.exists(body.getCode()), SecurityApiCode.ROLE_NOT_EXISTED,"角色不存在");

        Role role = mapper.map(body,Role.class);
        roleMapper.update(role);
        return role;
    }

    @Override
    public void delete(String code) {
        Role role = roleMapper.queryByCode(code);
        ApiPreconditions.is(null == role, SecurityApiCode.ROLE_NOT_EXISTED,"角色不存在");
        //删除role、map_roles_permissions、map_users_roles
        roleMapper.delete(role.getId());
    }

    @Override
    public void grantPermission(String code,List<Long> permissions) {
        Role role = roleMapper.queryByCode(code);
        ApiPreconditions.is(null == role, SecurityApiCode.ROLE_NOT_EXISTED,"角色不存在");
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
    public Page<Role> queryPage(Pageable pageable) {
        return roleMapper.queryPage(pageable);
    }

}
