package com.minlia.module.wallet.bean.entity;


import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.wallet.enumeration.WalletOperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WalletHistory extends AbstractEntity {

    /**
     * 钱包ID
     */
    private Long walletId;

    /**
     * 操作类型
     */
    private WalletOperationTypeEnum operationType;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 描述
     */
    private String desc;

}
