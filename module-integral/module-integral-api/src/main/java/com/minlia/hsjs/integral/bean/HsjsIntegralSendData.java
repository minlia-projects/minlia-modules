package com.minlia.hsjs.integral.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HsjsIntegralSendData {

    private Long uid;

    private Long quantity;

}