package com.minlia.module.pay.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.egzosn.pay.ali.bean.AliTransactionType;
import com.minlia.module.pay.entity.MerchantDetailsEntity;
import com.minlia.module.pay.enums.SysPayChannelEnum;
import com.minlia.module.pay.enums.SysPayMethodEnum;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author garen
 * @since 2021-05-14
 */
public interface MerchantDetailsService extends IService<MerchantDetailsEntity> {

    MerchantDetailsEntity getByTypeAndMethod(SysPayChannelEnum type);

    MerchantDetailsEntity getByTypeAndMethod(SysPayChannelEnum type, SysPayMethodEnum method);

}