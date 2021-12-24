package com.minlia.module.member.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.member.bean.SysAuthenticationRo;
import com.minlia.module.member.bean.SysMemberAuthenticationSro;
import com.minlia.module.member.entity.SysPersonalAuthenticationEntity;
import com.minlia.module.member.enums.MemberAuthenticationStatusEnum;
import com.minlia.module.member.mapper.SysPersonalAuthenticationMapper;
import com.minlia.module.member.service.SysPersonalAuthenticationService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 个人认证 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-09-08
 */
@Service
public class SysPersonalAuthenticationServiceImpl extends ServiceImpl<SysPersonalAuthenticationMapper, SysPersonalAuthenticationEntity> implements SysPersonalAuthenticationService {

    @Override
    public boolean authentication(SysMemberAuthenticationSro sro) {
        SysPersonalAuthenticationEntity entity = this.getOne(Wrappers.<SysPersonalAuthenticationEntity>lambdaQuery().eq(SysPersonalAuthenticationEntity::getUid, SecurityContextHolder.getUid()));
        if (null == entity) {
            entity = DozerUtils.map(sro, SysPersonalAuthenticationEntity.class);
            entity.setUid(SecurityContextHolder.getUid());
            entity.setStatus(MemberAuthenticationStatusEnum.PENDING);
            return this.save(entity);
        } else {
            ApiAssert.state(!MemberAuthenticationStatusEnum.PASSED.equals(entity.getStatus()), "已认证，请勿重复认证");
            DozerUtils.map(sro, entity);
            entity.setStatus(MemberAuthenticationStatusEnum.PENDING);
            return this.updateById(entity);
        }
    }

    @Override
    public boolean review(SysAuthenticationRo ro) {
        SysPersonalAuthenticationEntity entity = this.getById(ro.getId());
        entity.setStatus(ro.getStatus());
        entity.setReason(ro.getReason());
        return this.updateById(entity);
    }

    @Override
    public SysPersonalAuthenticationEntity latest() {
        return this.getOne(Wrappers.<SysPersonalAuthenticationEntity>lambdaQuery()
                .eq(SysPersonalAuthenticationEntity::getUid, SecurityContextHolder.getUid())
                .orderByDesc(SysPersonalAuthenticationEntity::getId).last("limit 1"));
    }

}
