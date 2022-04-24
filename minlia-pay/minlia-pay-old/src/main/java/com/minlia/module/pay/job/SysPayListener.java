package com.minlia.module.pay.job;

import com.minlia.module.pay.service.SysPayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author garen
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class SysPayListener {

    private final SysPayOrderService sysPayOrderService;

    /**
     * 订单失效
     */
    //@Scheduled(cron = "0 * * * * ?")
    //public void expire() {
    //    Page<SysPayOrderEntity> list = sysPayOrderService.list(Wrappers.<SysPayOrderEntity>lambdaQuery().eq(SysPayOrderEntity::getStatus, SysPayStatusEnum.UNPAID).lt(SysPayOrderEntity::getExpireTime, LocalDateTime.now()));
    //    for (SysPayOrderEntity orderEntity : list) {
    //        expire(orderEntity);
    //        try {
    //            switch (orderEntity.getBusinessType()) {
    //                case BLIND_BOX:
    //                    expireWithBox(orderEntity);
    //                    break;
    //                case FIRST:
    //                    expireWithProductSet(orderEntity);
    //                    break;
    //            }
    //        } catch (Exception e) {
    //            log.error("失效订单失败：", e);
    //        }
    //    }
    //}

}
