package com.minlia.module.riskcontrol.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 风控名单
 *
 * @author garen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_risk_list")
@Table(name = "sys_risk_list", uniqueConstraints = {@UniqueConstraint(columnNames = {"dimension", "value"})})
public class RiskList {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(value = javax.persistence.EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '类型'")
    private RiskTypeEnum type;

    @Enumerated(value = javax.persistence.EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '维度'")
    private EnumDimension dimension;

    @Column(nullable = false, columnDefinition = "VARCHAR(100) COMMENT '值'")
    private String value;

    @Column(nullable = false, insertable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    private LocalDateTime time;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '详情'")
    private String detail;

    /**
     * 维度枚举
     */
    @Getter
    @AllArgsConstructor
    public enum EnumDimension {
        IP(0, "IP"),
        MOBILE(1, "MOBILE"),
        EMAIIL(2, "EMAIIL"),
        DEVICEID(3, "DEVICEID");

        private Integer value;

        @EnumValue
        private String code;

    }

}