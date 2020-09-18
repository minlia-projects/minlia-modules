package com.minlia.module.lov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.lov.entity.SysLovEntity;

/**
 * <p>
 * LOV表 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
public interface LovService extends IService<SysLovEntity> {

    Boolean disable(Long id);

}
