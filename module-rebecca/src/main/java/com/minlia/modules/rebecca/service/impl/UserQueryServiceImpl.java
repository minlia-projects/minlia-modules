package com.minlia.modules.rebecca.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rebecca.bean.domain.User;
import com.minlia.modules.rebecca.bean.qo.UserQO;
import com.minlia.modules.rebecca.constant.UserCode;
import com.minlia.modules.rebecca.mapper.UserMapper;
import com.minlia.modules.rebecca.service.UserQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryByGuidAndNotNull(String guid) {
        User user = userMapper.queryOne(UserQO.builder().guid(guid).build());
        ApiAssert.notNull(user, UserCode.Message.NOT_EXISTS);
        return user;
    }

    @Override
    public User queryByUsernameOrCellphoneOrEmail(String arg) {
        return userMapper.queryByUsernameOrCellphoneOrEmail(arg);
    }

    @Override
    public long count(UserQO qro) {
        return userMapper.count(qro);
    }

    @Override
    public boolean exists(UserQO qro) {
        return userMapper.count(qro) > 0;
    }

    @Override
    public User queryOne(UserQO qro) {
        return userMapper.queryOne(qro);
    }

    @Override
    public List<User> queryList(UserQO qro) {
        return userMapper.queryList(qro);
    }

    @Override
    public PageInfo queryPage(UserQO qro, Pageable pageable) {
        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> userMapper.queryList(qro));
    }

}
