package com.minlia.module.district.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minlia.module.district.entity.SysDistrictEntity;

/**
 * <p>
 * 区域 服务类
 * </p>
 *
 * @author garen
 * @since 2021-04-06
 */
public interface SysDistrictService extends IService<SysDistrictEntity> {

    boolean init();

    boolean init1();
}
