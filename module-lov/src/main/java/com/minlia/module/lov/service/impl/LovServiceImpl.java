package com.minlia.module.lov.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.module.lov.entity.SysLovEntity;
import com.minlia.module.lov.mapper.SysLovMapper;
import com.minlia.module.lov.service.LovService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * LOV表 服务实现类
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Service
public class LovServiceImpl extends ServiceImpl<SysLovMapper, SysLovEntity> implements LovService {

    @Override
    public Boolean disable(Long id) {
        SysLovEntity entity = this.getById(id);
        entity.setDisFlag(!entity.getDelFlag());
        this.updateById(entity);
        return entity.getDisFlag();
    }

}
