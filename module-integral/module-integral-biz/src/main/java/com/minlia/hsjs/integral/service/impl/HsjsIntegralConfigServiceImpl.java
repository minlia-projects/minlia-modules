package com.minlia.hsjs.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.hsjs.integral.entity.HsjsIntegralConfigEntity;
import com.minlia.hsjs.integral.mapper.HsjsIntegralConfigMapper;
import com.minlia.hsjs.integral.service.HsjsIntegralConfigService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 积分-配置 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Service
public class HsjsIntegralConfigServiceImpl extends ServiceImpl<HsjsIntegralConfigMapper, HsjsIntegralConfigEntity> implements HsjsIntegralConfigService {

    @Override
    public HsjsIntegralConfigEntity getByType(String businessType) {
        return this.getOne(Wrappers.<HsjsIntegralConfigEntity>lambdaQuery().eq(HsjsIntegralConfigEntity::getBusinessType, businessType));
    }

}