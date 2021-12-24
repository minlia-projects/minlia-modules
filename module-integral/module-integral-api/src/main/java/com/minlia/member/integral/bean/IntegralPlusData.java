package com.minlia.member.integral.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IntegralPlusData {

    private Long uid;

    private Long businessId;

    private String businessType;

}