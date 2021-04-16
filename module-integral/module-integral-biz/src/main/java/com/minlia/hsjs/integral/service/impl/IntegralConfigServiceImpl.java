package com.minlia.hsjs.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.hsjs.integral.entity.IntegralConfigEntity;
import com.minlia.hsjs.integral.mapper.IntegralConfigMapper;
import com.minlia.hsjs.integral.service.IntegralConfigService;
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
public class IntegralConfigServiceImpl extends ServiceImpl<IntegralConfigMapper, IntegralConfigEntity> implements IntegralConfigService {

    @Override
    public IntegralConfigEntity getByType(String businessType) {
        return this.getOne(Wrappers.<IntegralConfigEntity>lambdaQuery().eq(IntegralConfigEntity::getBusinessType, businessType));
    }

}