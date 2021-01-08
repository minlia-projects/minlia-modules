package com.minlia.module.shipping.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.shipping.bean.ShippingAddressSro;
import com.minlia.module.shipping.entity.ShippingAddressEntity;

/**
 * <p>
 * 收货地址 服务类
 * </p>
 *
 * @author garen
 * @since 2021-01-04
 */
public interface ShippingAddressService extends IService<ShippingAddressEntity> {

    Boolean create(ShippingAddressSro sro);

    Boolean update(ShippingAddressSro sro);

    ShippingAddressEntity getDefault();

}