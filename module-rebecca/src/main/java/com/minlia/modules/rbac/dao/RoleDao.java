package com.minlia.modules.rbac.dao;

import com.minlia.cloud.dao.BatisDao;
import com.minlia.cloud.dao.Dao;
import com.minlia.modules.rbac.domain.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by will on 8/14/17.
 */
public interface RoleDao extends Dao<Role, Long> {
    Role findOneByCode(String code);

}
