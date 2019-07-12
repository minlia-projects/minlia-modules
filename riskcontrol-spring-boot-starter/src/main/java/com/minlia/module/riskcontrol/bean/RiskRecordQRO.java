package com.minlia.module.riskcontrol.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.riskcontrol.enums.RiskLevelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 风控记录
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskRecordQRO extends QueryRequest {

    private Long id;

    /**
     * 事件id
     */
    private String eventId;

    /**
     * 规则
     */
    private String eventDetails;

    /**
     * 场景
     */
    private String scene;

    /**
     * 风险等级
     */
    private RiskLevelEnum level;

    /**
     * 事件评分
     */
    private int score;

    /**
     * 时间
     */
    private LocalDateTime dateTime;

    /**
     * 详情
     */
    private String details;

}
