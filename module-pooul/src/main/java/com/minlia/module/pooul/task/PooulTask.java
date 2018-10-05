package com.minlia.module.pooul.task;

import com.minlia.cloud.body.Response;
import com.minlia.module.pooul.bean.domain.PooulOrderDO;
import com.minlia.module.pooul.bean.dto.PooulPayNotifyDTO;
import com.minlia.module.pooul.bean.dto.PooulPayNotifyData;
import com.minlia.module.pooul.bean.qo.PooulOrderQO;
import com.minlia.module.pooul.enumeration.TradeStateEnum;
import com.minlia.module.pooul.event.PooulEventPublisher;
import com.minlia.module.pooul.service.PooulOrderService;
import com.minlia.module.pooul.service.PooulPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/27.
 */
@Component
public class PooulTask {

    @Autowired
    private PooulPayService pooulPayService;

    @Autowired
    private PooulOrderService pooulOrderService;

    /**
     * 定时查询未付订单状态并更新
     */
    @Scheduled(cron = "0 0/5 * * * ?")
//    @Scheduled(cron = "0/10 * * * * ?")
    public void closeOrder() {
        //查询超过30分钟没付款的订单
        List<PooulOrderDO> orderDOS = pooulOrderService.list(PooulOrderQO.builder().payStatus(TradeStateEnum.UNPAID).gtCreateDateMinutes(30).build());
        for (PooulOrderDO pooulOrderDO : orderDOS) {
            //查询pooul订单状态
            Response response = pooulPayService.query(pooulOrderDO.getMchTradeId());
            if (response.isSuccess()) {
                Map data = (Map)response.getPayload();
                int trade_state = (int) data.get("trade_state");
//                0	交易成功
//                1	转入退款：支付成功后调用退款接口转入退款，只代表退款业务提交成功，具体退款状态请调用退款查询接口
//                2	未支付：交易订单生成后未支付
//                3	已关闭：交易生成后未支付，订单已关闭
//                4	已完结：交易支付成功，已完结，不可发起退款
//                5	已撤销：交易支付确认失败
//                6	支付中：用于反扫，当用户需要输入密码时的状态
                switch (trade_state) {
                    case 0:
                        pooulOrderDO.setPayStatus(TradeStateEnum.PAID);

                        //发布通知事件
                        PooulPayNotifyDTO notifyDTO = PooulPayNotifyDTO.builder()
                                .code(0)
                                .data(PooulPayNotifyData.builder().mchTradeId(pooulOrderDO.getMchTradeId()).build())
                                .build();
                        PooulEventPublisher.onPaid(notifyDTO);
                        break;
                    case 1:
                        pooulOrderDO.setPayStatus(TradeStateEnum.REFUNDED);
                        break;
                    case 2:
                        //未付账单直接关闭
                        pooulOrderDO.setPayStatus(TradeStateEnum.CLOSED);
                        pooulPayService.close(pooulOrderDO.getMchTradeId());
                        break;
                    case 3:
                        pooulOrderDO.setPayStatus(TradeStateEnum.CLOSED);
                        break;
                    case 4:
                        pooulOrderDO.setPayStatus(TradeStateEnum.SETTLED);
                        break;
                    case 5:
                        pooulOrderDO.setPayStatus(TradeStateEnum.CANCELED);
                        break;
                    case 6:
                        break;
                    default:
                }
                pooulOrderService.update(pooulOrderDO);
            }
        }
    }

//    /**
//     * 结算已支付的订单
//     */
//    @Scheduled(cron = "0 0/10 * * * ?")
//    public void settlement() {
////        Page<PooulOrderDO> page = PageHelper.startPage(0,100).doSelectPage(()-> pooulOrderService.list(PooulOrderQO.builder().payStatus(TradeStateEnum.UNPAID).gtCreateDateMinutes(30).build()));
//        List<PooulOrderDO> list = pooulOrderService.list(PooulOrderQO.builder().payStatus(TradeStateEnum.UNPAID).gtCreateDateMinutes(30).build());
//
//        for (PooulOrderDO pooulOrder : list) {
//            //内部转账
//            PooulInternalTransfersTO transfersTO = PooulInternalTransfersTO.builder()
//                    .payer_merchant_id("2849928048545130")
//                    .payee_merchant_id(pooulOrder.getMchTradeId())
//                    .voucher(pooulOrder.getMchTradeId())
//                    .amount(pooulOrder.getTotalFee())
//                    .transfer_type("内部转账")
//                    .op_user_id("-1")
//                    .build();
//            PooulDTO pooulDTO = pooulBalancesService.internalTransfers("2849928048545130",transfersTO);
//            if (pooulDTO.isSuccess()) {
//                //设置结算结算状态为已内部转账
//                pooulOrder.setSettledStatus(SettledStatusEnum.INTERNAL_TRANSFERRED);
//                pooulOrderService.update(pooulOrder);
//
//                //获取银行卡
//                PooulBankCardDO bankCard = pooulBankcardMapper.queryByMerchantId(pooulOrder.getMerchantId());
//                if (null != bankCard) {
//                    //提现
//                    PooulWithdrawTO withdrawTO = PooulWithdrawTO.builder()
//                            .mch_withdraw_id("WD00005")
//                            .withdraw_type(1)
//                            .bank_card_id(bankCard.getRecord_id())
//                            //5:网银互联
//                            .local_flag(5)
//                            .amount(pooulOrder.getTotalFee())
//                            .trade_fee(0)
//                            //订单号
//                            .voucher(pooulOrder.getMchTradeId())
//                            .remarks("提现")
//                            .op_user_id("-1")
//                            .build();
//                    Response response = pooulWithdrawService.withdraw(pooulOrder.getMchTradeId(), withdrawTO);
//                    if (response.isSuccess()) {
//                        //设置结算结算状态为已结算
//                        pooulOrder.setSettledStatus(SettledStatusEnum.SETTLED);
//                    } else {
//                        pooulOrder.setNotes(String.format("提现失败：%s-%s", response.getCode(), response.getMessage()));
//                    }
//                } else {
//                    pooulOrder.setNotes("内部转账银行卡未添加");
//                }
//            } else {
//                pooulOrder.setNotes(String.format("内部转账失败：%s-%s", pooulDTO.getCode(), pooulDTO.getMsg()));
//            }
//            pooulOrderService.update(pooulOrder);
//        }
//    }

}
