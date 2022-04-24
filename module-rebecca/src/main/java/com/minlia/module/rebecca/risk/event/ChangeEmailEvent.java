package com.minlia.module.rebecca.risk.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Data;

/**
 * @author garen
 */
@Data
public class ChangeEmailEvent extends Event {

    private String email;

}