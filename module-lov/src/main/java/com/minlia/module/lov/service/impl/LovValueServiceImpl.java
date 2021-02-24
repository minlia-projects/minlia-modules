package com.minlia.module.lov.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.i18n.util.LocaleUtils;
import com.minlia.module.lov.entity.SysLovEntity;
import com.minlia.module.lov.entity.SysLovItemEntity;
import com.minlia.module.lov.mapper.LovValueMapper;
import com.minlia.module.lov.service.LovService;
import com.minlia.module.lov.service.LovValueService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * LOV值集表 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Service
public class LovValueServiceImpl extends ServiceImpl<LovValueMapper, SysLovItemEntity> implements LovValueService {

    private final LovService lovService;

    public LovValueServiceImpl(LovService lovService) {
        this.lovService = lovService;
    }

    @Override
    public SysLovItemEntity create(SysLovItemEntity entity) {
        ApiAssert.state(lovService.count(new QueryWrapper<SysLovEntity>().lambda().eq(SysLovEntity::getId, entity.getLovId())) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        this.save(entity);
        return entity;
    }

    @Override
    public SysLovItemEntity update(SysLovItemEntity entity) {
        ApiAssert.state(lovService.count(new QueryWrapper<SysLovEntity>().lambda().eq(SysLovEntity::getId, entity.getLovId())) == 1, SystemCode.Message.DATA_NOT_EXISTS);
        this.updateById(entity);
        return entity;
    }

    @Override
    public Boolean disable(Long id) {
        SysLovItemEntity entity = this.getById(id);
        entity.setDisFlag(!entity.getDelFlag());
        this.updateById(entity);
        return entity.getDisFlag();
    }

    @Override
    public SysLovItemEntity get(String lovCode, String itemCode) {
        SysLovEntity lov = lovService.getByCode(lovCode);
        return null != lov ? getOne(Wrappers.<SysLovItemEntity>lambdaQuery()
                .eq(SysLovItemEntity::getLovId, lov.getId())
                .eq(SysLovItemEntity::getCode, itemCode)
                .eq(SysLovItemEntity::getLocale, LocaleUtils.locale())) : null;
    }

    @Override
    public String getName(String lovCode, String itemCode) {
        SysLovItemEntity item = get(lovCode, itemCode);
        return null != item ? item.getName() : null;
    }

}