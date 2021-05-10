package com.minlia.module.wallet.bean;

import com.minlia.module.wallet.enums.WalletOperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletUro {

    private Long uid;

    private BigDecimal amount;

    private WalletOperationTypeEnum type;

    private String remark;

}