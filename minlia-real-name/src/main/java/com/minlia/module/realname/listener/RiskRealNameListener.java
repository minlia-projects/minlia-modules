package com.minlia.module.realname.listener;

import com.minlia.module.realname.event.RiskRealNameTriggerEvent;
import com.minlia.module.rebecca.user.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 实名风控触发监听
 *
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class RiskRealNameListener {

    private final SysUserService sysUserService;

    @Async
    @EventListener
    public void risk(RiskRealNameTriggerEvent event) {
        Long uid = (Long) event.getSource();
        //sysUserService.lock(uid, 1440L);
    }

}