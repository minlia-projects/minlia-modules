package com.minlia.modules.rbac.backend.user.service;

import com.minlia.cloud.code.ApiCode;
import com.minlia.cloud.utils.ApiPreconditions;
import com.minlia.modules.rbac.backend.common.constant.SecurityApiCode;
import com.minlia.modules.rbac.backend.user.body.UserCreateRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserGarenRequestBody;
import com.minlia.modules.rbac.backend.user.body.UserUpdateRequestBody;
import com.minlia.modules.rbac.backend.user.entity.User;
import com.minlia.modules.rbac.backend.user.mapper.UserMapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
//    @Autowired
//    private RoleService roleService;
    @Autowired
    private UserQueryService userQueryService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User create(UserCreateRequestBody requestBody) {
        ApiPreconditions.is(userQueryService.exists(requestBody.getUsername()), ApiCode.NOT_NULL,"用户名已被使用");

        //验证推荐人
        if (StringUtils.isNotEmpty(requestBody.getReferee())) {
            User user = userMapper.queryByGuid(requestBody.getReferee());
            ApiPreconditions.is(null == user, SecurityApiCode.USER_REFEREE_NOT_FOUND,"推荐人不存在");
        }

        User user = User.builder()
                //TODO 多机部署时需要设置这里的数据中心与机器ID
//                .guid(new Long(new GuidGenerator(1l, 1l).nextId()).toString())
//        TODO  太长了，直接用表序列
                .guid(RandomUtils.nextInt(10000000,99999999) + "")
                .username(requestBody.getUsername())
                .referee(requestBody.getReferee())
                .password(bCryptPasswordEncoder.encode(requestBody.getPassword()))
                .enabled(Boolean.TRUE)
                .expired(Boolean.FALSE)
                .credentialsExpired(Boolean.FALSE)
                .locked(Boolean.FALSE)
                .lockLimit(NumberUtils.INTEGER_ZERO)
                .enabled(Boolean.FALSE)
                .build();
        userMapper.create(user);

        //授予角色
        if (CollectionUtils.isNotEmpty(requestBody.getRoles())) {
            this.grantRole(new UserGarenRequestBody());
            //TODO
        }

        return user;
    }

    @Override
    public User update(UserUpdateRequestBody body) {
        User user = userMapper.queryByGuid(body.getGuid());
        user.setPassword(bCryptPasswordEncoder.encode(body.getPassword()));
        userMapper.update(user);
        return user;
    }

    @Override
    public void delete(Long id) {
        //TODO
//        userMapper.delete(id);
    }

    @Override
    public void grantRole(UserGarenRequestBody requestBody) {
        User user = userMapper.queryByGuid(requestBody.getGuid());
        ApiPreconditions.is(null == user,ApiCode.NOT_FOUND);

        //TODO
//        Role role = roleService.findOneByCode(requestBody.getRoleCode());
//        ApiPreconditions.is(null == role,ApiCode.NOT_FOUND);


    }

    @Override
    public void revokeRole(UserGarenRequestBody requestBody) {
        User user = userMapper.queryByGuid(requestBody.getGuid());
        ApiPreconditions.is(null == user,ApiCode.NOT_FOUND);

        //TODO
//        Role role = roleService.findOneByCode(requestBody.getRoleCode());
//        ApiPreconditions.is(null == role,ApiCode.NOT_FOUND);

    }

    @Override
    public Boolean locked(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        if (user.getLocked()) {
            user.setLocked(false);
            user.setLockLimit(0);
            user.setLockTime(new Date());
        } else {
            user.setLocked(true);
//            user.setLockTime(user.getLockTime().plusMonths(1));
            user.setLockTime(DateUtils.addMonths(user.getLockTime(),1));
        }
        userMapper.locked(user);
        return !user.getLocked();
    }

    @Override
    public Boolean disabled(String guid) {
        User user = userQueryService.queryByGuidAndNotNull(guid);
        if (user.getEnabled()) {
            user.setEnabled(false);
        } else {
            user.setEnabled(true);
        }
        userMapper.disabled(user);
        return !user.getLocked();
    }

}
