package com.minlia.module.pay.bean;

import com.minlia.module.pay.enums.SysPayChannelEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysPayOrderDto<T> {

    private String orderNo;

    private SysPayChannelEnum channel;

    private T payload;

}