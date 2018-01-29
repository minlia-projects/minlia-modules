package com.minlia.modules.rbac.backend.permission.service;

import com.minlia.modules.rbac.backend.permission.body.PermissionUpdateBody;
import com.minlia.modules.rbac.backend.permission.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

/**
 * Created by will on 6/18/17.
 */
public interface PermissionService {

    void create(String code, String desc);

    void create(Set<Permission> permissions);

    Permission update(PermissionUpdateBody body);

    Permission queryById(Long id);

    Permission queryByCode(String code);

    Page<Permission> queryPage(Pageable pageable);

    Page<Permission> queryPageByRoleCodes(List<String> roleCodes, Pageable pageable);

    /**
     * 获取授权
     * @param roleCodes
     * @return
     */
    List<GrantedAuthority> getGrantedAuthority(List<String> roleCodes);

}
