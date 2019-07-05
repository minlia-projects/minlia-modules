package com.minlia.modules.rebecca.mapper;

import com.minlia.modules.rebecca.bean.domain.Permission;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by will on 8/23/17.
 */
@Mapper
public interface PermissionMapper {

    void create(Permission permission);

    void update(Permission permission);

    void grantAll(Long roleId);

    void clear();

    long countById(Long id);

    long countByCode(String code);

    Permission queryById(Long id);

    List<Permission> queryAll();

    List<String> queryCodesByRoleCodes(List<String> roleCodes);

    List<Permission> queryListByGuid(String guid);

    List<Permission> queryListByRoleCodes(List<String> roleCodes);

    List<Permission> queryList();

    List<Map> tree();

    List<String> oneLevel();

    List<String> twoLevel(String prefix);

    List<Map> threeLevel(String prefix);
}
