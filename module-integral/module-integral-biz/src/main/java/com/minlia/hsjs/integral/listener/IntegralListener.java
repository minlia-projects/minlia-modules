package com.minlia.hsjs.integral.listener;

import com.minlia.hsjs.integral.bean.IntegralMinusData;
import com.minlia.hsjs.integral.bean.IntegralPlusData;
import com.minlia.hsjs.integral.event.IntegralMinusEvent;
import com.minlia.hsjs.integral.event.IntegralPlusEvent;
import com.minlia.hsjs.integral.service.IntegralRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class IntegralListener {

    private final IntegralRecordService integralRecordService;

    @EventListener
    public void plus(IntegralPlusEvent event) {
        IntegralPlusData data = (IntegralPlusData) event.getSource();
        integralRecordService.plus(data);
    }

    @EventListener
    public void minus(IntegralMinusEvent event) {
        IntegralMinusData data = (IntegralMinusData) event.getSource();
        integralRecordService.minus(data);
    }

}