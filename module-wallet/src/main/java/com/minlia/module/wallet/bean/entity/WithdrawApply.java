//package com.minlia.module.wallet.v1.entity;
//
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.minlia.boot.persistence.constant.PersistenceConstants;
//import com.minlia.module.persistence.entity.AbstractAuditingEntity;
//import com.minlia.module.wallet.v1.enumeration.WithdrawStatusEnum;
//import lombok.*;
//
//import javax.persistence.*;
//import java.math.BigDecimal;
//
//@Entity
//@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "WithdrawApply")
//@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "WithdrawApply")
//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString(of = "id")
//public class WithdrawApply extends AbstractAuditingEntity {
//
//    private static final long serialVersionUID = 1L;
//
//    /**
//     * 用户ID
//     */
//    @Column(nullable = false)
//    @JsonProperty
//    private Long userId;
//
//    @Transient
//    @JsonProperty
//    private String username;
//
//    /**
//     * 持卡人
//     */
//    @JsonProperty
//    private String cardholder;
//
//    /**
//     * 银行名称
//     */
//    @JsonProperty
//    private String bankName;
//
//    /**
//     * 卡号
//     */
//    @JsonProperty
//    private String cardNumber;
//
//    /**
//     * 申请金额
//     */
//    @Column(nullable = false)
//    @JsonProperty
//    private BigDecimal applyAmount;
//
//    /**
//     * 结算金额
//     */
//    @Column(nullable = false)
//    @JsonProperty
//    private BigDecimal settledAmount;
//
//    /**
//     * 备注
//     */
//    @JsonProperty
//    private String note;
//
//    /**
//     * 提现状态
//     */
//    @JsonProperty
//    private WithdrawStatusEnum withdrawStatus;
//
//}
