package com.minlia.module.riskcontrol.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 风控配置
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_risk_drools_config")
public class RiskDroolsConfig {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, columnDefinition = "VARCHAR(200) NOT NULL COMMENT '规则键值'")
    private String ruleKey;

    @Column(columnDefinition = "VARCHAR(200) COMMENT '详情'")
    private String detail;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '时间'")
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime time;

    @Column(columnDefinition = "TINYINT(1) NOT NULL DEFAULT 0 COMMENT '禁用标识'")
    private Boolean disFlag;

    @Column(columnDefinition = "BIGINT NOT NULL COMMENT '查询时间段、多少秒之内的'")
    private Long periodSeconds;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1 COMMENT '危险阀值'")
    private Integer dangerThreshold;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1 COMMENT '警告阀值'")
    private Integer warningThreshold;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1 COMMENT '阀值评分'")
    private Integer thresholdScore;

    @Column(columnDefinition = "INT NOT NULL DEFAULT 1 COMMENT '超过阀值以上，每个评分'")
    private Integer perScore;

}