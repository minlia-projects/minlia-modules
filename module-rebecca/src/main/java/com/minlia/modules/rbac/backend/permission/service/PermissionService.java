package com.minlia.modules.rbac.backend.permission.service;

import com.github.pagehelper.PageInfo;
import com.minlia.modules.rbac.backend.permission.body.PermissionUpdateRequestBody;
import com.minlia.modules.rbac.backend.permission.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by will on 6/18/17.
 */
public interface PermissionService {

    void create(String code, String label);

    void create(Set<Permission> permissions);

    Permission update(PermissionUpdateRequestBody body);

    void grantAll(Long roleId);

    void clear();

    Long countById(Long id);

    Long countByCode(String code);

    List<Map<String,Object>> tree();

    List<Permission> queryAll();

    List<Permission> queryListByGuid(String guid);

    List<Permission> queryListByRoleCodes(List<String> roleCodes);

    PageInfo<Permission> queryPage(Pageable pageable);

    /**
     * 获取授权
     * @param roleCodes
     * @return
     */
    List<GrantedAuthority> getGrantedAuthority(List<String> roleCodes);

    List<String> getPermissionCodes(List<String> roleCodes);

}
