package com.minlia.module.rebecca.permission.mapper;

import com.minlia.module.rebecca.permission.entity.SysPermissionEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 权限点 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
public interface SysPermissionMapper extends BaseMapper<SysPermissionEntity> {

    int clear();

    List<String> oneLevel();

    List<String> twoLevel(String one);

    List<Map> threeLevel(String s);

    List<String> selectCodesByRoleId(Long roleId);

    List<String> selectCodesByRoleIds(List<Long> roleIds);

    List<Long> selectIdsByRoleCode(@Param("roleCode") String roleCode);

    List<Long> selectIdsByUserCode(@Param("userCode") String userCode);

    List<Long> selectIdsByRoleId(@Param("userId") Long userId);

}
