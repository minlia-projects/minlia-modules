package com.minlia.modules.rbac.dao;

import com.minlia.cloud.dao.BatisDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.data.mybatis.repository.annotation.Query;

/**
 * Created by will on 8/14/17.
 */
public interface UserDao extends BatisDao<User, Long> {

//    @Query(basic = true, operation = Query.Operation.select_one)
    public User findOneByUsernameOrEmailOrCellphone(String username,String email,String cellphone);

}
