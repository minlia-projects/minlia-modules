package com.minlia.module.unified.payment.service;


import com.minlia.module.unified.payment.entity.UnifiedOrder;

import java.util.List;

public interface UnifiedOrderService {

    int create(UnifiedOrder unifiedOrder);

    int update(UnifiedOrder unifiedOrder);

    int delete(Long id);

    long queryCount(UnifiedOrder unifiedOrder);

    UnifiedOrder queryOne(UnifiedOrder unifiedOrder);

    List<UnifiedOrder> queryList(UnifiedOrder unifiedOrder);

//    PageInfo<UnifiedOrder> queryPage(UnifiedOrder qro);

}
