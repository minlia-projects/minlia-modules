package com.minlia.modules.rbac.backend.permission.mapper;

import com.minlia.modules.rbac.backend.permission.entity.Permission;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by will on 8/23/17.
 */
@Component
public interface PermissionMapper {

    void create(Permission permission);

    void update(Permission permission);

    Permission queryById(Long id);

    Permission queryByCode(String code);

    Page<Permission> queryPage(Pageable pageable);

    List<Permission> queryPageByRoleCodes(List<String> roleCodes);

    Page<Permission> queryPageByRoleCodes(List<String> roleCodes, Pageable pageable);
}
