package com.minlia.modules.rbac.mapper;

import com.minlia.modules.rbac.bean.domain.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by will on 8/23/17.
 */
@Mapper
public interface RoleMapper {

    void create(Role role);

    void update(Role role);

    void delete(Long id);

    void grant(Long id, List<Long> permissions);

    int deleteRolePermission(Long id);

    Role queryById(Long id);

    Role queryByCode(String code);

    List<Role> queryByGuid(String guid);

    List<Long> queryIdByUserId(Long userId);

    List<String> queryCodeByUserId(Long userId);

    List<Role> queryList();

}
