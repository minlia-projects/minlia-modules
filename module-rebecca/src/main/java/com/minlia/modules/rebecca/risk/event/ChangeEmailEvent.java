package com.minlia.modules.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

@Data
public class ChangeEmailEvent extends Event {

    public final static String GUID = "guid";

    public final static String EMAIL = "email";

    private String guid;

    private String email;

}