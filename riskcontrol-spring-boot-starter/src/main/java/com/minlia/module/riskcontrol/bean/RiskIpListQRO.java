package com.minlia.module.riskcontrol.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.riskcontrol.entity.RiskIpList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskIpListQRO extends QueryRequest {

    private Long id;

    private RiskTypeEnum type;

    private RiskIpList.EnumCountry country;

    private Long start;

    private Long end;

    private LocalDateTime time;

    private Boolean disFlag;

}
