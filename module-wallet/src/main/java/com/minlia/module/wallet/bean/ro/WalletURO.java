package com.minlia.module.wallet.bean.ro;

import com.minlia.module.wallet.enumeration.WalletOperationTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletURO {

    private String guid;

    private BigDecimal amount;

    private WalletOperationTypeEnum operationType;

    private String desc;

}
