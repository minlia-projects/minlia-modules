package com.minlia.module.wallet.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.member.constant.SysMemberCode;
import com.minlia.module.member.entity.SysMemberEntity;
import com.minlia.module.member.service.SysMemberService;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.bean.SysWalletAliSro;
import com.minlia.module.wallet.entity.SysWalletAliEntity;
import com.minlia.module.wallet.mapper.SysWalletAliMapper;
import com.minlia.module.wallet.service.SysWalletAliService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 钱包-支付宝 服务实现类
 * </p>
 *
 * @author garen
 * @since 2022-01-12
 */
@Service
@RequiredArgsConstructor
public class SysWalletAliServiceImpl extends ServiceImpl<SysWalletAliMapper, SysWalletAliEntity> implements SysWalletAliService {

    private final SysMemberService sysMemberService;


    @Override
    public SysWalletAliEntity getByUid(Long uid) {
        return this.getOne(Wrappers.<SysWalletAliEntity>lambdaQuery().eq(SysWalletAliEntity::getUid, uid));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean save(SysWalletAliSro sro) {
        //验证二级密码
        boolean result = sysMemberService.verifyPassword(sro.getPassword());
        ApiAssert.state(result, SysMemberCode.Message.VERIFY_PASSWORD_FAILURE);

        //是否实名
        SysMemberEntity memberEntity = sysMemberService.getByUid(SecurityContextHolder.getUid());
        ApiAssert.state(memberEntity.getRealName(), SysMemberCode.Message.REAL_NAME_NOT_OPENED);

        SysWalletAliEntity entity = this.getByUid(SecurityContextHolder.getUid());
        if (Objects.nonNull(entity)) {
            entity.setNumber(sro.getNumber());
            return this.updateById(entity);
        } else {
            entity = SysWalletAliEntity.builder().uid(SecurityContextHolder.getUid()).name(memberEntity.getName()).number(sro.getNumber()).build();
            return this.save(entity);
        }
    }

}