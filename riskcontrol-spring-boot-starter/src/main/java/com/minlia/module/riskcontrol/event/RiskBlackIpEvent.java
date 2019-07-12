package com.minlia.module.riskcontrol.event;

import lombok.Data;

@Data
public class RiskBlackIpEvent extends Event {

    public RiskBlackIpEvent() {
        super();
        setScene("black_ip");
    }



}
