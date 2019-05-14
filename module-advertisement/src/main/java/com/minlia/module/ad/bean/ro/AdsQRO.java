package com.minlia.module.ad.bean.ro;

import com.minlia.module.ad.enumeration.LogicOperatorEnum;
import com.minlia.module.ad.enumeration.PlatformEnum;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdsQRO extends QueryRequest {

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 平台
     */
    private PlatformEnum platform;

    /**
     * 投放位置
     */
    private String position;

    /**
     * 是否启用
     */
    private Boolean enabled;

    private String attribute1;
    private LogicOperatorEnum attribute1Logic;

    private String attribute2;
    private LogicOperatorEnum attribute2Logic;

    private String attribute3;
    private LogicOperatorEnum attribute3Logic;

    private String attribute4;
    private LogicOperatorEnum attribute4Logic;

    private String attribute5;
    private LogicOperatorEnum attribute5Logic;


}