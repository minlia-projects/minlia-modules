package com.minlia.module.riskcontrol.entity;

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
@Entity(name = "mdl_risk_config")
public class RiskConfig {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String key;

    @Column(nullable = false)
    private String value;

    @Column(nullable = true)
    private String detail;

    @Column(nullable = false)
    private LocalDateTime time;


    /**
     * 查询时间段、多少秒之内的
     */
    @Column(nullable = false)
    private Long periodSeconds;

    /**
     * 危险阀值
     */
    @Column(nullable = false)
    private Integer dangerThreshold;

    /**
     * warningThreshold
     */
    @Column(nullable = false)
    private Integer warningThreshold;

    /**
     * thresholdScore
     */
    @Column(nullable = false)
    private Integer thresholdScore;

    /**
     * 超过阀值以上，每个评分
     */
    @Column(nullable = false)
    private Integer perScore;

}