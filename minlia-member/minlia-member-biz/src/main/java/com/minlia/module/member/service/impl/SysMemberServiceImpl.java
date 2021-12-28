package com.minlia.module.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.member.constant.SysMemberCode;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.member.entity.SysMemberEntity;
import com.minlia.module.member.mapper.SysMemberMapper;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员信息 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-12-28
 */
@Service
@RequiredArgsConstructor
public class SysMemberServiceImpl extends ServiceImpl<SysMemberMapper, SysMemberEntity> implements SysMemberService {

    private final SysUserRegisterService sysUserRegisterService;

    @Override
    public Response register(UserRegisterRo registerRo) {
        registerRo.setRoleCode(SysMemberConstants.DEFAULT_ROLE);
        Response<SysUserEntity> response = sysUserRegisterService.register(registerRo);
        ApiAssert.state(response.isSuccess(), SysMemberCode.Message.REGISTRATION_FAILURE);
        this.save(SysMemberEntity.builder().uid(response.getPayload().getId()).build());
        return response;
    }

}