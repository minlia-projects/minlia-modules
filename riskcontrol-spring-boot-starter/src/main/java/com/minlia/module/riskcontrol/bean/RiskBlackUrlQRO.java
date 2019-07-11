package com.minlia.module.riskcontrol.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.riskcontrol.entity.RiskBlackList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


/**
 * 黑名单，白名单，可疑名单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskBlackUrlQRO extends QueryRequest {

    private Long id;

    /**
     * 类型
     */
    private RiskBlackList.EnumType type;

//    /**
//     * 维度
//     */
//    private RiskBlackList.EnumDimension dimension;

    /**
     * 值
     */
    private String value;

    /**
     * 时间
     */
    private LocalDateTime time;

    /**
     * 详情
     */
    private String detail;

}
