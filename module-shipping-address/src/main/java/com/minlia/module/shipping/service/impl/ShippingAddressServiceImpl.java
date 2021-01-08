package com.minlia.module.shipping.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.shipping.bean.ShippingAddressSro;
import com.minlia.module.shipping.constant.ShippingAddressCode;
import com.minlia.module.shipping.entity.ShippingAddressEntity;
import com.minlia.module.shipping.mapper.ShippingAddressMapper;
import com.minlia.module.shipping.service.ShippingAddressService;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 收货地址 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-01-04
 */
@Service
public class ShippingAddressServiceImpl extends ServiceImpl<ShippingAddressMapper, ShippingAddressEntity> implements ShippingAddressService {

    @Override
    public Boolean create(ShippingAddressSro sro) {
        ShippingAddressEntity shippingAddressEntity = DozerUtils.map(sro, ShippingAddressEntity.class);
        shippingAddressEntity.setUid(MinliaSecurityContextHolder.getUid());
        long count = this.count(Wrappers.<ShippingAddressEntity>lambdaQuery().eq(ShippingAddressEntity::getUid, MinliaSecurityContextHolder.getUid()));
        ApiAssert.state(count <= 10, ShippingAddressCode.Message.ADD_UP_TO_10_ADDRESSES);
        shippingAddressEntity.setDefFlag(count > 0 ? false : true);
        return this.save(shippingAddressEntity);
    }

    @Override
    public Boolean update(ShippingAddressSro sro) {
        LambdaQueryWrapper queryWrapper = Wrappers.<ShippingAddressEntity>lambdaQuery().eq(ShippingAddressEntity::getId, sro.getId()).eq(ShippingAddressEntity::getUid, MinliaSecurityContextHolder.getUid());
        ShippingAddressEntity shippingAddressEntity = this.getOne(queryWrapper);
        DozerUtils.map(sro, shippingAddressEntity);
        return this.updateById(shippingAddressEntity);
    }

    @Override
    public ShippingAddressEntity getDefault() {
        LambdaQueryWrapper queryWrapper = Wrappers.<ShippingAddressEntity>lambdaQuery().eq(ShippingAddressEntity::getDefFlag, true).eq(ShippingAddressEntity::getUid, MinliaSecurityContextHolder.getUid());
        return this.getOne(queryWrapper);
    }

}