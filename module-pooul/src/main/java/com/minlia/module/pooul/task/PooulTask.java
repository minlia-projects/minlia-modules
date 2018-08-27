package com.minlia.module.pooul.task;

import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import com.minlia.module.pooul.enumeration.PayStatusEnum;
import com.minlia.module.pooul.mapper.PooulOrderMapper;
import com.minlia.module.pooul.service.PooulPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by garen on 2018/8/27.
 */
@Component
public class PooulTask {

    @Autowired
    private PooulPayService pooulPayService;

    @Autowired
    private PooulOrderMapper pooulOrderMapper;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void closeOrder() {
        //查询超过30分钟没付款的订单
        List<PooulOrderDO> orderDOS = pooulOrderMapper.queryList(PooulOrderQO.builder().payStatus(PayStatusEnum.UNPAID).gtCreateDateMinutes(30).build());
        for (PooulOrderDO orderDO : orderDOS) {
            pooulPayService.close(orderDO.getMchTradeId());
        }
    }

}
