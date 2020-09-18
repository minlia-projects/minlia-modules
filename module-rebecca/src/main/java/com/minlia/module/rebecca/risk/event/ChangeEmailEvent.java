package com.minlia.module.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

@Data
public class ChangeEmailEvent extends Event {

    private Long uid;

    private String email;

}