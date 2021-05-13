package com.minlia.hsjs.integral.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.hsjs.integral.entity.IntegralUserEntity;
import com.minlia.hsjs.integral.mapper.IntegralUserMapper;
import com.minlia.hsjs.integral.service.IntegralUserService;
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
public class IntegralUserServiceImpl extends ServiceImpl<IntegralUserMapper, IntegralUserEntity> implements IntegralUserService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void plus(Long uid, Long quantity) {
        IntegralUserEntity entity = getByUid(uid);
        entity.setBalance(entity.getBalance() + quantity);
        entity.setTotal(entity.getTotal() + quantity);
        entity.setGrandTotal(entity.getGrandTotal() + quantity);
        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void minus(Long uid, Long quantity) {
        IntegralUserEntity entity = getByUid(uid);
        entity.setBalance(entity.getBalance() - quantity);
        entity.setTotal(entity.getTotal() - quantity);
        this.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public IntegralUserEntity getByUid(Long uid) {
        IntegralUserEntity entity = this.getOne(Wrappers.<IntegralUserEntity>lambdaQuery().eq(IntegralUserEntity::getUid, uid));
        if (Objects.isNull(entity)) {
            entity = IntegralUserEntity.builder().uid(uid).balance(NumberUtils.LONG_ZERO).freeze(NumberUtils.LONG_ZERO).total(NumberUtils.LONG_ZERO).grandTotal(NumberUtils.LONG_ZERO).build();
            this.save(entity);
        }
        return entity;
    }

}