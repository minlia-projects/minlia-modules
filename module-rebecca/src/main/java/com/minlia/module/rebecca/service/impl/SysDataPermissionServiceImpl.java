package com.minlia.module.rebecca.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.minlia.module.data.constant.SysDataConstants;
import com.minlia.module.rebecca.entity.SysDataPermissionEntity;
import com.minlia.module.rebecca.mapper.SysDataPermissionMapper;
import com.minlia.module.rebecca.service.SysDataPermissionService;
import com.minlia.module.redis.util.RedisUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 数据权限 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-03-03
 */
@Service
public class SysDataPermissionServiceImpl extends ServiceImpl<SysDataPermissionMapper, SysDataPermissionEntity> implements SysDataPermissionService {

    private final static List<String> method = Lists.newArrayList("selectById", "selectOne", "selectBatchIds", "selectList", "selectPage", "selectCount");

    @Override
    public boolean createAllSelect(String method) {
        return false;
    }

    @Override
    public boolean create(SysDataPermissionEntity entity) {
        RedisUtils.sSet(SysDataConstants.Redis.DATA_PERMISSION_PREFIX, entity.getMethod());
        return this.save(entity);
    }

    @Override
    public boolean update(SysDataPermissionEntity entity) {
        if (entity.getDisFlag()) {
            RedisUtils.setRemove(SysDataConstants.Redis.DATA_PERMISSION_PREFIX, entity.getMethod());
        } else {
            RedisUtils.sSet(SysDataConstants.Redis.DATA_PERMISSION_PREFIX, entity.getMethod());
        }
        return this.updateById(entity);
    }

    @Override
    public boolean delete(Long id) {
        SysDataPermissionEntity entity = this.getById(id);
        RedisUtils.setRemove(SysDataConstants.Redis.DATA_PERMISSION_PREFIX, entity.getMethod());
        return this.removeById(id);
    }

}