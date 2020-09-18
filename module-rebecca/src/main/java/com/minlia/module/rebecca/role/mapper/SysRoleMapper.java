package com.minlia.module.rebecca.role.mapper;

import com.minlia.module.rebecca.role.entity.SysRoleEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-08-22
 */
public interface SysRoleMapper extends BaseMapper<SysRoleEntity> {

    List<String> selectCodesByUserId(@Param("uid") Long uid);

    List<Long> selectIdsByUserId(@Param("uid") Long uid);

}
