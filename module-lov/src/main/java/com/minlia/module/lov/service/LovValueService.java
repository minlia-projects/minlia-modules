package com.minlia.module.lov.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.lov.entity.SysLovItemEntity;

/**
 * <p>
 * LOV值集表 服务类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
public interface LovValueService extends IService<SysLovItemEntity> {

    SysLovItemEntity create(SysLovItemEntity entity);
    
    SysLovItemEntity update(SysLovItemEntity entity);

    Boolean disable(Long id);

    SysLovItemEntity get(String lovCode, String itemCode);

    String getName(String lovCode, String itemCode);
}
