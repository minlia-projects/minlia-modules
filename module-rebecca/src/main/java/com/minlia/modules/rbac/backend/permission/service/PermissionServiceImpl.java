package com.minlia.modules.rbac.backend.permission.service;

import com.minlia.modules.rbac.backend.permission.body.PermissionUpdateBody;
import com.minlia.modules.rbac.backend.permission.entity.Permission;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by maitha.manyala on 10/6/15.
 */
@Slf4j
@Service
public class PermissionServiceImpl implements PermissionService {
    @Override
    public void create(String code, String desc) {

    }

    @Override
    public void create(Set<Permission> permissions) {

    }

    @Override
    public Permission update(PermissionUpdateBody body) {
        return null;
    }

    @Override
    public Permission queryById(Long id) {
        return null;
    }

    @Override
    public Permission queryByCode(String code) {
        return null;
    }

    @Override
    public Page<Permission> queryPage(Pageable pageable) {
        return null;
    }

    @Override
    public Page<Permission> queryPageByRoleCodes(List<String> roleCodes, Pageable pageable) {
        return null;
    }

    @Override
    public List<GrantedAuthority> getGrantedAuthority(List<String> roleCodes) {
        return null;
    }

//    @Autowired
//    private PermissionMapper permissionMapper;
//
//    @Override
//    @Transactional
//    public void create(String code, String desc) {
//        if (!this.exists(code)) {
//            Permission permission = new Permission();
//            permission.setCode(code);
//            permission.setDesc(desc);
//            permissionMapper.create(permission);
//        }
//    }
//
//    @Override
//    @Transactional
//    public void create(Set<Permission> permissions) {
//        if (CollectionUtils.isNotEmpty(permissions)) {
//            for (Permission permission : permissions) {
//                this.create(permission.getCode(),permission.getDesc());
//            }
//        }
//    }
//
//    @Override
//    @Transactional
//    public Permission update(PermissionUpdateBody body) {
//        Permission permission = permissionMapper.queryById(body.getId());
//        permission.setDesc(body.getDesc());
//        permissionMapper.update(permission);
//        return permission;
//    }
//
//    private boolean exists(String code) {
//        return null != permissionMapper.queryByCode(code);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Permission queryById(Long id) {
//        return permissionMapper.queryById(id);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Permission queryByCode(String code) {
//        return permissionMapper.queryByCode(code);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Page<Permission> queryPage(Pageable pageable) {
//        return permissionMapper.queryPage(pageable);
//    }
//
//    private List<Permission> queryPageByRoleCodes(List<String> roleCodes) {
//        return permissionMapper.queryPageByRoleCodes(roleCodes);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Page<Permission> queryPageByRoleCodes(List<String> roleCodes, Pageable pageable) {
//        return permissionMapper.queryPageByRoleCodes(roleCodes,pageable);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(value = { "permission" }, key = "#p0",unless="#result==null")
//    public List<GrantedAuthority> getGrantedAuthority(List<String> roleCodes) {
//        final List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//        final List<Permission> permissions = queryPageByRoleCodes(roleCodes);
//        if (CollectionUtils.isNotEmpty(permissions)) {
//            for (Permission permission : permissions) {
//                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
//            }
//        }
//        return authorities;
//    }

}
