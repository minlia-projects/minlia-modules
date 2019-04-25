package com.minlia.module.wallet.listener;

import com.minlia.module.wallet.service.WalletService;
import com.minlia.modules.rbac.bean.domain.User;
import com.minlia.modules.rbac.event.RegistrationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class WalletEventListener {

    @Autowired
    private WalletService walletService;

    @Async
    @EventListener
    public void onComplete(RegistrationEvent event) {
        //注册完成初始化钱包
        User user = (User) event.getSource();
        walletService.init(user.getGuid());
    }

}
