package com.minlia.hsjs.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.hsjs.integral.entity.HsjsIntegralUserEntity;
import com.minlia.hsjs.integral.mapper.HsjsIntegralUserMapper;
import com.minlia.hsjs.integral.service.HsjsIntegralUserService;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 积分-用户 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
@Service
public class HsjsIntegralUserServiceImpl extends ServiceImpl<HsjsIntegralUserMapper, HsjsIntegralUserEntity> implements HsjsIntegralUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void plus(Long uid, Long quantity) {
        HsjsIntegralUserEntity entity = getByUid(uid);
        entity.setAvailable(entity.getAvailable() + quantity);
        entity.setTotal(entity.getTotal() + quantity);
        entity.setGrandTotal(entity.getGrandTotal() + quantity);
        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void minus(Long uid, Long quantity) {
        HsjsIntegralUserEntity entity = getByUid(uid);
        entity.setAvailable(entity.getAvailable() - quantity);
        entity.setTotal(entity.getTotal() - quantity);
        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HsjsIntegralUserEntity getByUid(Long uid) {
        HsjsIntegralUserEntity entity = this.getOne(Wrappers.<HsjsIntegralUserEntity>lambdaQuery().eq(HsjsIntegralUserEntity::getUid, uid));
        if (Objects.isNull(entity)) {
            entity = HsjsIntegralUserEntity.builder().uid(uid).available(NumberUtils.LONG_ZERO).freeze(NumberUtils.LONG_ZERO).total(NumberUtils.LONG_ZERO).grandTotal(NumberUtils.LONG_ZERO).build();
            this.save(entity);
        }
        return entity;
    }

}