package com.minlia.module.aliyun.dypls.event;

import com.minlia.module.aliyun.dypls.service.DyplsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by garen on 2018/05/18.
 */
@Component
public class DyplsUnbindEventListener {

    @Autowired
    private DyplsService dyplsService;

    @Async
    @EventListener
    public void onBind(DyplsUnbindEvent event) {
        String subsId = (String) event.getSource();
        dyplsService.deleteBySecretNo(subsId);
    }

}
