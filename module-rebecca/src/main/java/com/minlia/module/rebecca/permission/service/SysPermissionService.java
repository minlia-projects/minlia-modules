package com.minlia.module.rebecca.permission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.rebecca.permission.entity.SysPermissionEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 权限点 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
public interface SysPermissionService extends IService<SysPermissionEntity> {

    Boolean clear();

    List<Map<String, Object>> tree();

    List<String> getCodesByRoleId(Long roleId);
    List<String> getCodesByRoleId(List<Long> roleIds);

    List<Long> getIdsByRoleId(Long roleId);
    List<Long> getIdsByRoleCode(String roleCode);

    List<Long> getIdsByUserId(Long userId);
    List<Long> getIdsByUserCode(String userCode);

    Set<Long> getAllIds();

}
