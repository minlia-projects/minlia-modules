package com.minlia.module.wallet.listener;

import com.minlia.module.unified.payment.bean.OrderPaidNotificationResponse;
import com.minlia.module.unified.payment.event.OrderPaidEvent;
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

    @EventListener
    public void onRecharge(OrderPaidEvent event) {
        OrderPaidNotificationResponse response = (OrderPaidNotificationResponse) event.getSource();
        if (response.getMerchantTradeNo().startsWith("RO") && response.getBody().equals("充值")) {
            //更新钱包余额 TODO
        }
    }

}
