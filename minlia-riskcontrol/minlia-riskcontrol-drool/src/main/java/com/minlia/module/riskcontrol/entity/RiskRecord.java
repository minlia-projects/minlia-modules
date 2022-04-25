package com.minlia.module.riskcontrol.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
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
@Entity(name = "sys_risk_record")
//@Table(name = "mdl_risk_record", uniqueConstraints = {@UniqueConstraint(columnNames={"dimension","value"})})
public class RiskRecord {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT '事件id'")
    private String eventId;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) COMMENT '场景'")
    private String scene;

    @Column(nullable = true, columnDefinition = "VARCHAR(50) COMMENT '场景值'")
    private String sceneValue;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, columnDefinition = "VARCHAR(20) COMMENT '风险等级'")
    private RiskLevelEnum level;

    @Column(nullable = true, columnDefinition = "BIGINT COMMENT '计数'")
    private Long count;

    @Column(nullable = false, columnDefinition = "INT COMMENT '事件评分'")
    private int score;

    @Column(nullable = true, columnDefinition = "INT COMMENT 'IP地址'")
    private String ip;

    @Column(insertable = false, updatable = false, columnDefinition = "timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime dateTime;

    @Column(columnDefinition = "VARCHAR(500) COMMENT '详情'")
    private String details;

    @Column(nullable = true, columnDefinition = "INT COMMENT '用户编号'")
    private String guid;

    @Column(nullable = false, columnDefinition = "VARCHAR(500) COMMENT '规则'")
    private String eventDetails;

}