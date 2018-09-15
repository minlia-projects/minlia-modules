package com.minlia.modules.rbac.backend.user.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.modules.rbac.backend.common.constant.RebaccaCode;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
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
        User user = userMapper.queryOne(UserQueryRequestBody.builder().guid(guid).build());
        ApiAssert.notNull(user, RebaccaCode.Message.USER_NOT_EXISTED);
        return user;
    }

    @Override
    public User queryByUsernameOrCellphoneOrEmail(String arg) {
        return userMapper.queryByUsernameOrCellphoneOrEmail(arg);
    }

    @Override
    public long count(UserQueryRequestBody requestBody) {
        return userMapper.count(requestBody);
    }

    @Override
    public boolean exists(UserQueryRequestBody requestBody) {
        return userMapper.count(requestBody) > 0;
    }

    @Override
    public User queryOne(UserQueryRequestBody requestBody) {
        return userMapper.queryOne(requestBody);
    }

    @Override
    public List<User> queryList(UserQueryRequestBody requestBody) {
        return userMapper.queryList(requestBody);
    }

    @Override
    public PageInfo queryPage(UserQueryRequestBody requestBody, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()-> userMapper.queryList(requestBody));
    }

}
