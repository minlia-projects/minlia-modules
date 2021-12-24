package com.minlia.module.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.entity.SysPersonalAuthenticationEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 个人认证 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
public interface SysPersonalAuthenticationMapper extends BaseMapper<SysPersonalAuthenticationEntity> {

    SysMemberInfoVo selectMemberByUid(@Param("uid") Long uid);

    Page<SysMemberInfoVo> selectMemberPage(@Param("page") Page page, @Param("qro") SysMemberQro qro);

}