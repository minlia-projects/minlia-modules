package com.minlia.module.member.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.cloud.body.Response;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.entity.SysMemberEntity;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;

/**
 * <p>
 * 会员信息 服务类
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
public interface SysMemberService extends IService<SysMemberEntity> {

    Response register(UserRegisterRo registerRo);

    Response realName(SysRealNameCro cro);

    boolean setPassword(String password, String verificationCode);

    boolean verifyPassword(String password);

    SysMemberInfoVo me();

    Page<SysMemberInfoVo> page(SysMemberQro qro);

    SysMemberEntity getByUid(Long uid);
}