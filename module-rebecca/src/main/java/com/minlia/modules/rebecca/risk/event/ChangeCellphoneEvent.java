package com.minlia.modules.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

@Data
public class ChangeCellphoneEvent extends Event {

    public final static String GUID = "guid";

    public final static String CELLPHONE = "cellphone";

    private String guid;

    private String cellphone;

}