package com.minlia.hsjs.integral.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.hsjs.integral.bean.HsjsIntegralAddData;
import com.minlia.hsjs.integral.bean.HsjsIntegralSendData;
import com.minlia.hsjs.integral.entity.HsjsIntegralConfigEntity;
import com.minlia.hsjs.integral.entity.HsjsIntegralRecordEntity;
import com.minlia.hsjs.integral.event.HsjsIntegralSendEvent;
import com.minlia.hsjs.integral.mapper.HsjsIntegralRecordMapper;
import com.minlia.hsjs.integral.service.HsjsIntegralConfigService;
import com.minlia.hsjs.integral.service.HsjsIntegralRecordService;
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
public class HsjsIntegralRecordServiceImpl extends ServiceImpl<HsjsIntegralRecordMapper, HsjsIntegralRecordEntity> implements HsjsIntegralRecordService {

    private final HsjsIntegralConfigService hsjsIntegralConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(HsjsIntegralAddData data) {
        HsjsIntegralConfigEntity config = hsjsIntegralConfigService.getByType(data.getBusinessType());
        if (Objects.nonNull(config)) {
            HsjsIntegralRecordEntity recordEntity = DozerUtils.map(data, HsjsIntegralRecordEntity.class);
            recordEntity.setQuantity(config.getQuantity());
            this.save(recordEntity);
            HsjsIntegralSendEvent.publish(HsjsIntegralSendData.builder().uid(data.getUid()).quantity(config.getQuantity()).build());
        }
    }

}