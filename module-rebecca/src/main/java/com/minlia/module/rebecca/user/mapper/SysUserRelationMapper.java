package com.minlia.module.rebecca.user.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.rebecca.user.bean.SysUserRelationQro;
import com.minlia.module.rebecca.user.bean.SysUserRelationVo;
import com.minlia.module.rebecca.user.entity.SysUserRelationEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户-关系 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
public interface SysUserRelationMapper extends BaseMapper<SysUserRelationEntity> {

    Page<SysUserRelationVo> detailsPage(@Param("page") Page page, @Param("qro") SysUserRelationQro qro);

}
