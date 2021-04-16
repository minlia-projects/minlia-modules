package com.minlia.hsjs.integral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.hsjs.integral.bean.IntegralMinusData;
import com.minlia.hsjs.integral.bean.IntegralPlusData;
import com.minlia.hsjs.integral.entity.IntegralConfigEntity;
import com.minlia.hsjs.integral.entity.IntegralRecordEntity;
import com.minlia.hsjs.integral.mapper.IntegralRecordMapper;
import com.minlia.hsjs.integral.service.IntegralConfigService;
import com.minlia.hsjs.integral.service.IntegralRecordService;
import com.minlia.hsjs.integral.service.IntegralUserService;
import com.minlia.module.dozer.util.DozerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 积分-记录 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Service
@RequiredArgsConstructor
public class IntegralRecordServiceImpl extends ServiceImpl<IntegralRecordMapper, IntegralRecordEntity> implements IntegralRecordService {

    private final IntegralUserService integralUserService;
    private final IntegralConfigService integralConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void plus(IntegralPlusData data) {
        IntegralConfigEntity config = integralConfigService.getByType(data.getBusinessType());
        if (Objects.nonNull(config)) {
            IntegralRecordEntity recordEntity = DozerUtils.map(data, IntegralRecordEntity.class);
            recordEntity.setQuantity(config.getQuantity());
            this.save(recordEntity);
            integralUserService.plus(data.getUid(), config.getQuantity());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void minus(IntegralMinusData data) {
        IntegralRecordEntity recordEntity = DozerUtils.map(data, IntegralRecordEntity.class);
        recordEntity.setQuantity(-recordEntity.getQuantity());
        this.save(recordEntity);
        integralUserService.minus(data.getUid(), data.getQuantity());
    }

}