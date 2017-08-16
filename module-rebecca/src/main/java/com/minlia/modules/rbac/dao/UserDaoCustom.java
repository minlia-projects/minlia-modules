package com.minlia.modules.rbac.dao;

import com.minlia.modules.rbac.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mybatis.repository.annotation.Query;

/**
 * Created by will on 8/16/17.
 */
public interface UserDaoCustom {

    @Query
    Page<User> findUseMapper22(Pageable pageable);

}
