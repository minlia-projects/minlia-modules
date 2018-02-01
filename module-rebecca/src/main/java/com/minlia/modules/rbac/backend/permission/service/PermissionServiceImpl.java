package com.minlia.modules.rbac.backend.permission.service;

import com.minlia.modules.rbac.backend.permission.body.PermissionUpdateBody;
import com.minlia.modules.rbac.backend.permission.entity.Permission;
import com.minlia.modules.rbac.backend.permission.mapper.PermissionMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(String code, String label) {
        if (!this.exists(code)) {
            Permission permission = new Permission();
            permission.setCode(code);
            permission.setLabel(label);
            permissionMapper.create(permission);
        }
    }

    @Override
    @Transactional
    public void create(Set<Permission> permissions) {
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (Permission permission : permissions) {
                this.create(permission.getCode(),permission.getLabel());
            }
        }
    }

    @Override
    @Transactional
    public Permission update(PermissionUpdateBody body) {
        Permission permission = permissionMapper.queryById(body.getId());
        permission.setLabel(body.getLabel());
        permissionMapper.update(permission);
        return permission;
    }

    @Override
    public void clear() {
        permissionMapper.clear();
    }

    private boolean exists(String code) {
        return permissionMapper.countByCode(code) > 0;
    }

    @Override
    @Transactional(readOnly = true)
    public Long countById(Long id) {
        return permissionMapper.countById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countByCode(String code) {
        return permissionMapper.countByCode(code);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> queryListByRoleCodes(List<String> roleCodes) {
        return permissionMapper.queryListByRoleCodes(roleCodes);
    }

    @Transactional(readOnly = true)
    public List<String> queryCodesByRoleCodes(List<String> roleCodes) {
        return permissionMapper.queryCodesByRoleCodes(roleCodes);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Permission> queryPageByRoleCodes(List<String> roleCodes, Pageable pageable) {
        return permissionMapper.queryPageByRoleCodes(roleCodes,pageable);
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(value = { "permission" }, key = "#p0",unless="#result==null")
    public List<GrantedAuthority> getGrantedAuthority(List<String> roleCodes) {
        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        final List<String> permissions = queryCodesByRoleCodes(roleCodes);
        if (CollectionUtils.isNotEmpty(permissions)) {
            for (String permission : permissions) {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        }
        return authorities;
    }

}
