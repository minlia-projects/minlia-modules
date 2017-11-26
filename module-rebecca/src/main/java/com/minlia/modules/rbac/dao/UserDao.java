package com.minlia.modules.rbac.dao;

import com.minlia.cloud.dao.BatisDao;
import com.minlia.modules.rbac.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mybatis.repository.annotation.Query;
import org.springframework.data.mybatis.repository.support.MybatisRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by will on 8/14/17.
 */
@Transactional(readOnly = true)
public interface UserDao extends BatisDao<User, Long>,UserDaoCustom  {

    //    @Query(basic = true, operation = Query.Operation.select_one)


    /**
     * 查找一个用户, 按用户名 邮箱或电话号码
     * @param username
     * @param email
     * @param cellphone
     * @return
     */
    User findOneByUsernameOrEmailOrCellphone(String username, String email, String cellphone);


    @Query(value = "findUseMapper444")
    Page<User> findUseMapper444(Pageable pageable);


}
