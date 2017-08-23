package com.minlia.modules.rbac.dao;

import com.minlia.cloud.dao.Dao;
import com.minlia.modules.rbac.domain.Permission;
import com.minlia.modules.rbac.domain.Role;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.annotation.Query;

/**
 * Created by will on 8/14/17.
 */
public interface PermissionDao extends Dao<Permission, Long> {
    Permission findByCode(String code);

    @Query
    List<Permission> findByRoles_Id(Long id);
}
