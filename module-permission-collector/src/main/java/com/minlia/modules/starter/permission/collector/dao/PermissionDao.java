package com.minlia.modules.starter.permission.collector.dao;

import com.minlia.cloud.dao.BatisDao;
import com.minlia.modules.starter.permission.collector.entity.Permission;

/**
 * Created by qianyi on 2017/8/14.
 * 权限Code和description扫描 Dao接口
 */
public interface PermissionDao extends BatisDao<com.minlia.modules.starter.permission.collector.entity.Permission,Long>{


    /**
     * 根据权限Code查询权限点是否存在
     * @param code
     * @return
     */
    public Permission findByCode(String code);



}
