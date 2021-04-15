package com.minlia.hsjs.integral.listener;

import com.minlia.hsjs.integral.bean.HsjsIntegralMinusData;
import com.minlia.hsjs.integral.bean.HsjsIntegralPlusData;
import com.minlia.hsjs.integral.event.HsjsIntegralMinusEvent;
import com.minlia.hsjs.integral.event.HsjsIntegralPlusEvent;
import com.minlia.hsjs.integral.service.HsjsIntegralRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class HsjsIntegralListener {

    private final HsjsIntegralRecordService hsjsIntegralRecordService;

    @EventListener
    public void plus(HsjsIntegralPlusEvent event) {
        HsjsIntegralPlusData data = (HsjsIntegralPlusData) event.getSource();
        hsjsIntegralRecordService.plus(data);
    }

    @EventListener
    public void minus(HsjsIntegralMinusEvent event) {
        HsjsIntegralMinusData data = (HsjsIntegralMinusData) event.getSource();
        hsjsIntegralRecordService.minus(data);
    }

}