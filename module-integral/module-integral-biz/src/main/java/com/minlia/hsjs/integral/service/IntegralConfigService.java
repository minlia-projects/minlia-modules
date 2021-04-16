package com.minlia.hsjs.integral.service;

import com.minlia.hsjs.integral.entity.IntegralConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 积分-配置 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
public interface IntegralConfigService extends IService<IntegralConfigEntity> {

    IntegralConfigEntity getByType(String businessType);

}