package com.minlia.module.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.member.bean.SysMemberQro;
import com.minlia.module.member.bean.vo.SysMemberInfoVo;
import com.minlia.module.member.constant.SysMemberCode;
import com.minlia.module.member.constant.SysMemberConstants;
import com.minlia.module.member.entity.SysMemberEntity;
import com.minlia.module.member.mapper.SysMemberMapper;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.realname.bean.SysRealNameCro;
import com.minlia.module.realname.service.SysRealNameService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.rebecca.user.bean.UserRegisterRo;
import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.service.SysUserRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private final SysRealNameService sysRealNameService;
    private final SysUserRegisterService sysUserRegisterService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response register(UserRegisterRo registerRo) {
        registerRo.setRoleCode(SysMemberConstants.DEFAULT_ROLE);
        Response<SysUserEntity> response = sysUserRegisterService.register(registerRo);
        ApiAssert.state(response.isSuccess(), SysMemberCode.Message.REGISTRATION_FAILURE);
        this.save(SysMemberEntity.builder().uid(response.getPayload().getId()).build());
        return response;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Response realName(SysRealNameCro cro) {
        cro.setUid(SecurityContextHolder.getUid());
        Response realNameResponse = sysRealNameService.auth(cro);

        SysMemberEntity entity = this.getByUid(SecurityContextHolder.getUid());
        entity.setName(cro.getName());
        entity.setIdNumber(cro.getIdNumber());
        entity.setRealName(realNameResponse.isSuccess());
        this.updateById(entity);
        return realNameResponse;
    }

    @Override
    public SysMemberInfoVo me() {
        return this.baseMapper.selectDetailsByUid(SecurityContextHolder.getUid());
    }

    @Override
    public Page<SysMemberInfoVo> page(SysMemberQro qro) {
        return this.baseMapper.selectDetailsPage(qro.getPage(), qro);
    }

    public SysMemberEntity getByUid(Long uid) {
        return this.getOne(Wrappers.<SysMemberEntity>lambdaQuery().eq(SysMemberEntity::getUid, uid));
    }

}