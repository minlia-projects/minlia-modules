package com.minlia.module.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.entity.SysMemberEntity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 会员信息 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
public interface SysMemberMapper extends BaseMapper<SysMemberEntity> {

    SysMemberInfoVo selectDetailsByUid(@Param("uid") Long uid);

    Page<SysMemberInfoVo> selectDetailsPage(@Param("page") Page page, @Param("qro") SysMemberQro qro);

}