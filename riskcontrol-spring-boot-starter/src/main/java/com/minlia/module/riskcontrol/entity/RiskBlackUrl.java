package com.minlia.module.riskcontrol.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 黑名单，白名单，可疑名单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mdl_risk_black_url")
@Table(name = "mdl_risk_black_url", uniqueConstraints = {@UniqueConstraint(columnNames = {"type", "value"})})
public class RiskBlackUrl {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = javax.persistence.EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '类型'")
    private EnumType type;

//    @Enumerated(value = javax.persistence.EnumType.STRING)
//    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '维度'")
//    private EnumDimension dimension;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) COMMENT '值'")
    private String value;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    private LocalDateTime time;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '详情'")
    private String detail;

//    /**
//     * 维度枚举
//     */
//    public static enum EnumDimension {
//        MOBILE,
//        EMAIIL,
//        IP,
//        DEVICEID;
//    }

    /**
     * 类型枚举
     */
    public static enum EnumType {
        BLACK,
        WHITE,
        GRAY;
    }

}