package com.minlia.module.rebecca.user.listener;

import com.minlia.hsjs.integral.bean.HsjsIntegralSendData;
import com.minlia.hsjs.integral.event.HsjsIntegralSendEvent;
import com.minlia.module.rebecca.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class HsjsIntegralEventListener {

    private final SysUserService sysUserService;

    @EventListener
    public void onPublish(HsjsIntegralSendEvent event) {
        HsjsIntegralSendData data = (HsjsIntegralSendData) event.getSource();
        sysUserService.addIntegral(data.getUid(), data.getQuantity());
    }

}