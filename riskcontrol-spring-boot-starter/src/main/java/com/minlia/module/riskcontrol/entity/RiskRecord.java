package com.minlia.module.riskcontrol.entity;

import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * 风控记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "mdl_risk_record")
//@Table(name = "mdl_risk_record", uniqueConstraints = {@UniqueConstraint(columnNames={"dimension","value"})})
public class RiskRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT '事件id'")
    private String eventId;

    @Column(nullable = false, columnDefinition = "VARCHAR(500) COMMENT '规则'")
    private String eventDetails;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT '场景'")
    private String scene;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '风险等级'")
    private RiskLevelEnum level;

    @Column(nullable = false, columnDefinition = "INT COMMENT '事件评分'")
    private int score;

    @Column(insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    private LocalDateTime dateTime;

    @Column(columnDefinition = "VARCHAR(500) COMMENT '详情'")
    private String details;

}
