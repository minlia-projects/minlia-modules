package com.minlia.modules.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

@Data
public class ChangeCellphoneEvent extends Event {

    private String guid;

    private String cellphone;

}