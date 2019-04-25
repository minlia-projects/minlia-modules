package com.minlia.module.unified.payment.service.impl;

import com.minlia.module.unified.payment.entity.UnifiedOrder;
import com.minlia.module.unified.payment.mapper.UnifiedOrderMapper;
import com.minlia.module.unified.payment.service.UnifiedOrderService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnifiedServiceImpl implements UnifiedOrderService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private UnifiedOrderMapper unifiedOrderMapper;

    @Override
    public int create(UnifiedOrder unifiedOrder) {
        return unifiedOrderMapper.insert(unifiedOrder);
    }

    @Override
    public int update(UnifiedOrder unifiedOrder) {
        return unifiedOrderMapper.update(unifiedOrder);
    }

    @Override
    public int delete(Long id) {
        return unifiedOrderMapper.delete(id);
    }

    @Override
    public long queryCount(UnifiedOrder unifiedOrder) {
        return unifiedOrderMapper.queryCount(unifiedOrder);
    }

    @Override
    public UnifiedOrder queryOne(UnifiedOrder unifiedOrder) {
        return unifiedOrderMapper.queryOne(unifiedOrder);
    }

    @Override
    public List<UnifiedOrder> queryList(UnifiedOrder unifiedOrder) {
        return unifiedOrderMapper.queryList(unifiedOrder);
    }

//    @Override
//    public PageInfo<UnifiedOrder> queryPage(UnifiedOrder order) {
//        return PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> unifiedOrderMapper.queryList(qro));
//    }

}
