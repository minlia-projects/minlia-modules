package com.minlia.module.member.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.member.mapper.SysPersonalAuthenticationMapper;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 会员 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
@Service
@RequiredArgsConstructor
public class SysMemberServiceImpl implements SysMemberService {

    private final SysUserRegisterService sysUserRegisterService;
    private final SysPersonalAuthenticationMapper sysPersonalAuthenticationMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response register(UserRegisterRo registerRo) {
        registerRo.setRoleCode(SysMemberConstants.DEFAULT_ROLE);
        return sysUserRegisterService.register(registerRo);
    }

    @Override
    public SysMemberInfoVo me() {
        return sysPersonalAuthenticationMapper.selectMemberByUid(SecurityContextHolder.getUid());
    }

    @Override
    public Page<SysMemberInfoVo> page(SysMemberQro qro) {
        return sysPersonalAuthenticationMapper.selectMemberPage(qro.getPage(), qro);
    }

}