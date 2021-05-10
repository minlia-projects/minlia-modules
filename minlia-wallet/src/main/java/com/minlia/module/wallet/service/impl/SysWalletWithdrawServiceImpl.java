package com.minlia.module.wallet.service.impl;

import cn.hutool.core.util.NumberUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.rebecca.context.SecurityContextHolder;
import com.minlia.module.wallet.bean.WalletUro;
import com.minlia.module.wallet.bean.WalletWithdrawApplyRo;
import com.minlia.module.wallet.bean.WalletWithdrawApprovalRo;
import com.minlia.module.wallet.config.WalletConfig;
import com.minlia.module.wallet.constant.WalletCode;
import com.minlia.module.wallet.entity.SysWalletEntity;
import com.minlia.module.wallet.entity.SysWalletWithdrawEntity;
import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import com.minlia.module.wallet.enums.WithdrawStatusEnum;
import com.minlia.module.wallet.mapper.SysWalletWithdrawMapper;
import com.minlia.module.wallet.service.SysWalletService;
import com.minlia.module.wallet.service.SysWalletWithdrawService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 钱包-提现 服务实现类
 * </p>
 *
 * @author garen
 * @since 2021-05-10
 */
@Service
@RequiredArgsConstructor
public class SysWalletWithdrawServiceImpl extends ServiceImpl<SysWalletWithdrawMapper, SysWalletWithdrawEntity> implements SysWalletWithdrawService {

    private final WalletConfig walletConfig;
    private final SysWalletService sysWalletService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysWalletWithdrawEntity apply(WalletWithdrawApplyRo applyRo) {
        SysWalletEntity walletEntity = sysWalletService.getByUid(SecurityContextHolder.getUid());
        ApiAssert.state(NumberUtil.isGreaterOrEqual(applyRo.getAmount(), walletConfig.getMinimumWithdrawalAmount()), WalletCode.Message.LESS_THAN_MINIMUM_WITHDRAWAL_AMOUNT);
        ApiAssert.state(NumberUtil.isGreaterOrEqual(walletEntity.getBalance(), applyRo.getAmount()), WalletCode.Message.BALANCE_NOT_ENOUGH);

        //插入数据
        SysWalletWithdrawEntity withdrawEntity = SysWalletWithdrawEntity.builder()
                .uid(SecurityContextHolder.getUid())
                .walletId(walletEntity.getId())
                .channel(applyRo.getChannel())
                .amount(applyRo.getAmount())
                .payee(applyRo.getPayee())
                .account(applyRo.getAccount())
                .status(WithdrawStatusEnum.PENDING)
                .build();
        this.save(withdrawEntity);

        //更新钱包冻结金额,可用余额
        WalletUro walletRequestBody = WalletUro.builder()
                .uid(walletEntity.getUid())
                .amount(applyRo.getAmount())
                .type(WalletOperationTypeEnum.WITHDRAW_APPLY)
                .build();
        sysWalletService.update(walletRequestBody);

        //判断是否秒批 TODO
        if (false) {
//            this.approval(WithdrawApprovalRequestBody.builder().id(withdrawApply.getId()).settlementAmount(withdrawApply.getApplyAmount()).note(withdrawApply.getNote()).build());
        }

        return withdrawEntity;
    }

    @Override
    public boolean approval(WalletWithdrawApprovalRo approvalRo) {
        return false;
    }
//
//    @Override
//    public synchronized WithdrawApply approval(WithdrawApprovalRequestBody requestBody) {
//        //先不考虑驳回 TODO
//        WithdrawApply withdrawApply = repository.findOne(requestBody.getId());
//        ApiPreconditions.is(null == withdrawApply,ApiCode.NOT_FOUND,"提现记录不存在");
//        ApiPreconditions.is(withdrawApply.getWithdrawStatus().equals(WithdrawStatusEnum.settled),ApiCode.NOT_AUTHORIZED,"已结算");
//        ApiPreconditions.is(withdrawApply.getWithdrawStatus().equals(WithdrawStatusEnum.reject),ApiCode.NOT_AUTHORIZED,"已驳回");
//        User user = userQueryService.findOne(withdrawApply.getUserId());
//
//        //更新状态为完成
//        withdrawApply.setWithdrawStatus(WithdrawStatusEnum.settled);
//        withdrawApply.setSettledAmount(withdrawApply.getApplyAmount());
//        repository.save(withdrawApply);
//
//        //更新钱包总额及冻结金额
//        WalletURO walletRequestBody = WalletURO.builder()
//                .userId(user.getId())
//                .amount(withdrawApply.getApplyAmount())
//                .walletOperationType(WalletOperationTypeEnum.WITHDRAW_SETTLED)
//                .note(withdrawApply.getNote())
//                .build();
//        walletService.update(walletRequestBody);
//
//        //TODO 调用第三方代付接口
//
//        return withdrawApply;
//    }
//

}