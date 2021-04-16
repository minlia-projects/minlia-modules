package com.minlia.hsjs.integral.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.hsjs.integral.entity.IntegralUserEntity;

/**
 * <p>
 * 积分-用户 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-15
 */
public interface IntegralUserService extends IService<IntegralUserEntity> {

    void plus(Long uid, Long quantity);

    void minus(Long uid, Long quantity);

    IntegralUserEntity getByUid(Long uid);

}