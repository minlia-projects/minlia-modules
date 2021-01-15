package com.minlia.module.shipping.mapper;

import com.minlia.module.shipping.entity.ShippingAddressEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 收货地址 Mapper 接口
 * </p>
 *
 * @author garen
 * @since 2021-01-04
 */
public interface ShippingAddressMapper extends BaseMapper<ShippingAddressEntity> {

    int updateDefFlag(@Param("uid") Long uid, @Param("defFlag") boolean defFlag);

}
