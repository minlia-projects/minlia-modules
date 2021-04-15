package com.minlia.hsjs.integral.listener;

import com.minlia.hsjs.integral.bean.HsjsIntegralAddData;
import com.minlia.hsjs.integral.event.HsjsIntegralAddEvent;
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
    public void onPublish(HsjsIntegralAddEvent event) {
        HsjsIntegralAddData data = (HsjsIntegralAddData) event.getSource();
        hsjsIntegralRecordService.create(data);
    }

}