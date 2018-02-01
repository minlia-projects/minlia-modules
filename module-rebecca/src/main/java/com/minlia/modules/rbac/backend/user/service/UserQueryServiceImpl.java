package com.minlia.modules.rbac.backend.user.service;

import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.backend.user.body.UserQueryRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.common.constant.SecurityApiCode;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserQueryServiceImpl implements UserQueryService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User queryById(Long id) {
        return userMapper.queryById(id);
    }

    @Override
    public User queryByGuid(String guid) {
        return userMapper.queryByGuid(guid);
    }

    @Override
    public User queryByGuidAndNotNull(String guid) {
        User user = userMapper.queryByGuid(guid);
        ApiPreconditions.is(null == user, SecurityApiCode.USER_NOT_EXISTED,"用户不存在");
        return user;
    }

    @Override
    public User queryByUsername(String username) {
        return userMapper.queryByUsername(username);
    }

    @Override
    public User queryByCellphone(String cellphone) {
        return userMapper.queryByUsername(cellphone);
    }

    @Override
    public boolean exists(String username) {
        return null != this.queryByUsername(username);
    }

    @Override
    public List<User> queryList(UserQueryRequestBody body) {
        return userMapper.queryList(body);
    }

    @Override
    public Page queryPage(UserQueryRequestBody body, Pageable pageable) {
        return userMapper.queryPage(body,pageable);
    }

    @Override
    public User queryByUsernameOrCellphoneOrEmail(String username, String email, String cellphone) {
        return userMapper.queryByUsernameOrCellphoneOrEmail(username,email,cellphone);
    }

}
