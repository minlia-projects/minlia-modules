package com.minlia.module.pay.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.pay.entity.MerchantDetailsEntity;
import com.minlia.module.pay.enums.SysPayChannelEnum;
import com.minlia.module.pay.mapper.MerchantDetailsMapper;
import com.minlia.module.pay.service.MerchantDetailsService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
@Service
public class MerchantDetailsServiceImpl extends ServiceImpl<MerchantDetailsMapper, MerchantDetailsEntity> implements MerchantDetailsService {

    @Override
    public MerchantDetailsEntity getByTypeAndMethod(SysPayChannelEnum type, String method) {
        return this.getOne(Wrappers.<MerchantDetailsEntity>lambdaQuery()
                .eq(MerchantDetailsEntity::getPayType, type)
                .eq(MerchantDetailsEntity::getPayMethod, method)
                .eq(MerchantDetailsEntity::getDisFlag, false), false);
    }

}