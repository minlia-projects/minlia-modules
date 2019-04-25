package com.minlia.module.unified.payment.mapper;

import com.minlia.module.unified.payment.entity.UnifiedOrder;

import java.util.List;

public interface UnifiedOrderMapper {

    int insert(UnifiedOrder order);

    int update(UnifiedOrder order);

    int delete(Long id);

    long queryCount(UnifiedOrder order);

    UnifiedOrder queryOne(UnifiedOrder order);

    List<UnifiedOrder> queryList(UnifiedOrder order);

}