package com.minlia.modules.rbac.backend.permission.mapper;

import com.minlia.modules.rbac.backend.permission.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by will on 8/23/17.
 */
@Mapper
public interface PermissionMapper {

    void create(Permission permission);

    void update(Permission permission);

    void clear();

    long countById(Long id);

    long countByCode(String code);

    Permission queryById(Long id);

    List<String> queryCodesByRoleCodes(List<String> roleCodes);

    List<Permission> queryListByRoleCodes(List<String> roleCodes);

    Page<Permission> queryPageByRoleCodes(List<String> roleCodes, Pageable pageable);

}
