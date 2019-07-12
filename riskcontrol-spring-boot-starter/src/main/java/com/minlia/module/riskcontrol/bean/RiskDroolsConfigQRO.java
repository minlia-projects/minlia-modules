package com.minlia.module.riskcontrol.bean;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
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
public class RiskDroolsConfigQRO extends QueryRequest {

    private Long id;

    private String ruleKey;

    private String detail;

    private LocalDateTime time;

    private Boolean disFlag;

    private Long periodSeconds;

    private Integer dangerThreshold;

    private Integer warningThreshold;

    private Integer thresholdScore;

    private Integer perScore;

}