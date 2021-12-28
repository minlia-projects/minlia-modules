package com.minlia.module.member.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;

/**
 * <p>
 * 会员 服务类
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
public interface SysMemberService {

    Response register(UserRegisterRo registerRo);

    SysMemberInfoVo me();

    Page<SysMemberInfoVo> page(SysMemberQro qro);
}