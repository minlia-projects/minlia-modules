//package com.minlia.module.wallet.v1.domain;
//
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.minlia.boot.persistence.constant.PersistenceConstants;
//import com.minlia.module.persistence.entity.AbstractAuditingEntity;
//import lombok.*;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.SequenceGenerator;
//import javax.persistence.Table;
//import java.math.BigDecimal;
//
//@Entity
//@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Wallet")
//@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "Wallet")
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString(of = "id")
//
///**
// * 钱包
// */
//public class Wallet extends AbstractAuditingEntity {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 用户ID
//     */
//    @Column(nullable = false,unique = true)
//    @JsonProperty
//    private Long userId;
//
//    /**
//     * 总金额
//     */
//    @Column(nullable = false,precision = 12,scale = 2)
//    @JsonProperty
//    private BigDecimal totalAmount;
//
//    /**
//     * 冻结金额
//     */
//    @Column(nullable = false,precision = 10,scale = 2)
//    @JsonProperty
//    private BigDecimal frozenAmount;
//
//    /**
//     * 余额
//     */
//    @Column(nullable = false,precision = 10,scale = 2)
//    @JsonProperty
//    private BigDecimal balance;
//
//    /**
//     * 总提现金额
//     */
//    @JsonProperty
//    private BigDecimal totalWithdrawAmount;
//
//    /**
//     * 总流水
//     */
//    @Column(nullable = false,precision = 12,scale = 2)
//    @JsonProperty
//    private BigDecimal flowAmount;
//
//}
