//package com.minlia.module.wallet.v1.domain;
//
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.minlia.boot.persistence.constant.PersistenceConstants;
//import com.minlia.module.persistence.entity.AbstractAuditingEntity;
//import com.minlia.module.wallet.v1.enumeration.WalletOperationTypeEnum;
//import lombok.*;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//
//@Entity
//@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "WalletHistory")
//@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "WalletHistory")
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString(of = "id")
//public class WalletHistory extends AbstractAuditingEntity {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 钱包ID
//     */
//    @Column(nullable = false)
//    @JsonIgnore
//    private Long walletId;
//
//    /**
//     * 操作类型
//     */
//    @Column(nullable = false)
//    @JsonProperty
//    private WalletOperationTypeEnum walletOperationType;
//
//    /**
//     * 金额
//     */
//    @Column(nullable = false,precision = 10,scale = 2)
//    @JsonProperty
//    private BigDecimal amount;
//
//    /**
//     * 备注
//     */
//    @JsonProperty
//    private String note;
//
//}
