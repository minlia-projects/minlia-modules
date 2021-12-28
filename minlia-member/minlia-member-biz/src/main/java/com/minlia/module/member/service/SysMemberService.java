package com.minlia.module.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.cloud.body.Response;
import com.minlia.module.member.entity.SysMemberEntity;
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

}