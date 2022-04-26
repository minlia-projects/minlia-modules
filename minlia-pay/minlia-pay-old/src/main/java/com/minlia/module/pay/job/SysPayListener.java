package com.minlia.module.pay.job;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.module.pay.entity.SysPayOrderEntity;
import com.minlia.module.pay.enums.SysPayStatusEnum;
import com.minlia.module.pay.service.SysPayOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

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
    @Scheduled(cron = "0/20 * * * * ?")
    public void expire() {
        Page<SysPayOrderEntity> page = sysPayOrderService.page(new Page<>(0, 100),
                Wrappers.<SysPayOrderEntity>lambdaQuery()
                        .eq(SysPayOrderEntity::getStatus, SysPayStatusEnum.UNPAID)
                        .lt(SysPayOrderEntity::getExpireTime, LocalDateTime.now()));
        log.info("获取到期未支付订单：共{}笔", page.getTotal());
        for (SysPayOrderEntity orderEntity : page.getRecords()) {
            try {
                sysPayOrderService.async(orderEntity.getOrderNo());
            } catch (Exception e) {
                log.error("失效订单失败：", e);
            }
        }
    }

}