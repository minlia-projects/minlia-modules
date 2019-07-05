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

    /**
     * 事件id
     */
    @Column(nullable = false, length = 50)
    private String eventId;


    /**
     * 规则
     */
    @Column(nullable = false)
    private String eventDetails;

    /**
     * 场景
     */
//    @Column(nullable = false, length = 50)
    private String scene;

    /**
     * 风险等级
     */
    @Enumerated
    @Column(nullable = false)
    private RiskLevelEnum level;

    /**
     * 事件评分
     */
    private int score;

    /**
     * 时间
     */
    @Column(insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    private LocalDateTime time;

    /**
     * 详情
     */
    @Column
    private String details;

}
