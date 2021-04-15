package com.minlia.hsjs.integral.service;

import com.minlia.hsjs.integral.entity.HsjsIntegralConfigEntity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 积分-配置 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
public interface HsjsIntegralConfigService extends IService<HsjsIntegralConfigEntity> {

    HsjsIntegralConfigEntity getByType(String businessType);

}