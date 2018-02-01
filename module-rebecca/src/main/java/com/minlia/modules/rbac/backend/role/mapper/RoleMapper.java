package com.minlia.modules.rbac.backend.role.mapper;

import com.minlia.modules.rbac.backend.role.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

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

    Role queryById(Long id);

    Role queryByCode(String code);

    List<Role> queryByGuid(String guid);

    List<Long> queryIdByUserId(Long userId);

    List<String> queryCodeByUserId(Long userId);

    Page<Role> queryPage(Pageable pageable);
}
