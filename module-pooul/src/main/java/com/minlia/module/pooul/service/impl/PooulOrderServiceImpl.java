package com.minlia.module.pooul.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import com.minlia.module.pooul.enumeration.SettledStatusEnum;
import com.minlia.module.pooul.mapper.PooulOrderMapper;
import com.minlia.module.pooul.service.PooulOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PooulOrderServiceImpl implements PooulOrderService {

    @Autowired
    private PooulOrderMapper pooulOrderMapper;

    @Override
    public void create(PooulOrderDO pooulOrderDO) {
        pooulOrderDO.setSettledStatus(SettledStatusEnum.UNSETTLEMENT);
        pooulOrderMapper.create(pooulOrderDO);
    }

    @Override
    public void update(PooulOrderDO pooulOrderDO) {
        pooulOrderMapper.update(pooulOrderDO);
    }

    @Override
    public PooulOrderDO queryByMchTradeId(String mchTradeId) {
        return pooulOrderMapper.one(PooulOrderQO.builder().mchTradeId(mchTradeId).build());
    }

    @Override
    public PooulOrderDO one(PooulOrderQO qo) {
        return pooulOrderMapper.one(qo);
    }

    @Override
    public List<PooulOrderDO> list(PooulOrderQO qo) {
        return pooulOrderMapper.list(qo);
    }

    @Override
    public PageInfo<PooulOrderDO> page(PooulOrderQO qo, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize()).doSelectPageInfo(()->pooulOrderMapper.list(qo));
    }

}
