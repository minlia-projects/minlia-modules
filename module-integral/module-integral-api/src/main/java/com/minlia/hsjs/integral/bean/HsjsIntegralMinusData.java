package com.minlia.hsjs.integral.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HsjsIntegralMinusData {

    private Long uid;

    private Long businessId;

    private String businessType;

    private Long quantity;

}