package com.minlia.modules.rbac.backend.permission.mapper;

import com.minlia.modules.rbac.backend.permission.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

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

    List<Permission> queryAll();

    List<String> queryCodesByRoleCodes(List<String> roleCodes);

    List<Permission> queryListByGuid(String guid);

    List<Permission> queryListByRoleCodes(List<String> roleCodes);

    Page<Permission> queryPage(RowBounds rowBounds);

    List<String> oneLevel();

    List<String> twoLevel(String prefix);

    List<Map> threeLevel(String prefix);

}
