package com.minlia.modules.rebecca.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.minlia.module.common.constant.SymbolConstants;
import com.minlia.modules.rebecca.bean.domain.Permission;
import com.minlia.modules.rebecca.bean.to.PermissionUTO;
import com.minlia.modules.rebecca.mapper.PermissionMapper;
import com.minlia.modules.rebecca.service.PermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
    public Permission update(PermissionUTO body) {
        Permission permission = permissionMapper.queryById(body.getId());
        permission.setLabel(body.getLabel());
        permissionMapper.update(permission);
        return permission;
    }

    @Override
    public void grantAll(Long roleId) {
        permissionMapper.grantAll(roleId);
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
    public List<Map<String,Object>> tree() {
        List<Map<String,Object>> ones = Lists.newArrayList();
        List<String> oneLevel = permissionMapper.oneLevel();

        for (String one : oneLevel) {
            List<Map<String,Object>> twos = Lists.newArrayList();
            List<String> twoLevel = permissionMapper.twoLevel(one);
            for (String two : twoLevel) {
                List<Map<String,Object>> threes = Lists.newArrayList();
                List<Map> threeLevel = permissionMapper.threeLevel(one + SymbolConstants.DOT + two + SymbolConstants.DOT);
                for (Map three : threeLevel) {
                    threes.add(three);
                }
                Map map = new HashMap();
                map.put("name",two);
                map.put("children",threes);
                twos.add(map);
            }
            Map map = new HashMap();
            map.put("name",one);
            map.put("children",twos);
            ones.add(map);
        }
        return ones;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> queryAll() {
        return permissionMapper.queryAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Permission> queryListByGuid(String guid) {
        return permissionMapper.queryListByGuid(guid);
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
    public PageInfo<Permission> queryPage(Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> permissionMapper.queryList());

    }

    @Override
    @Transactional(readOnly = true)
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

    @Override
    public List<String> getPermissionCodes(List<String> roleCodes) {
        return permissionMapper.queryCodesByRoleCodes(roleCodes);
    }

}
