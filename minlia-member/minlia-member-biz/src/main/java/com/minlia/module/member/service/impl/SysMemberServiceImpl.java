package com.minlia.module.member.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.mapper.SysPersonalAuthenticationMapper;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

    private final SysPersonalAuthenticationMapper sysPersonalAuthenticationMapper;

    @Override
    public SysMemberInfoVo me() {
        return sysPersonalAuthenticationMapper.selectMemberByUid(SecurityContextHolder.getUid());
    }

    @Override
    public Page<SysMemberInfoVo> page(SysMemberQro qro) {
        return sysPersonalAuthenticationMapper.selectMemberPage(qro.getPage(), qro);
    }

}