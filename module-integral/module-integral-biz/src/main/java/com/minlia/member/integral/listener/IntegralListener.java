package com.minlia.member.integral.listener;

import com.minlia.member.integral.bean.IntegralMinusData;
import com.minlia.member.integral.bean.IntegralPlusData;
import com.minlia.member.integral.event.IntegralMinusEvent;
import com.minlia.member.integral.event.IntegralPlusEvent;
import com.minlia.member.integral.service.IntegralRecordService;
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