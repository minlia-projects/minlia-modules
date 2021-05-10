package com.minlia.module.wallet.listener;

import com.minlia.module.rebecca.user.entity.SysUserEntity;
import com.minlia.module.rebecca.user.event.SysUserCreateEvent;
import com.minlia.module.wallet.service.SysWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WalletEventListener {

    private final SysWalletService sysWalletService;

    @Async
    @EventListener
    public void onComplete(SysUserCreateEvent event) {
        //注册完成初始化钱包
        SysUserEntity user = (SysUserEntity) event.getSource();
        sysWalletService.init(user.getId());
    }

//    @EventListener
//    public void onRecharge(OrderPaidEvent event) {
//        OrderPaidNotificationResponse response = (OrderPaidNotificationResponse) event.getSource();
//        if (response.getMerchantTradeNo().startsWith("RO") && response.getBody().equals("充值")) {
//            //更新钱包余额 TODO
//        }
//    }

}
