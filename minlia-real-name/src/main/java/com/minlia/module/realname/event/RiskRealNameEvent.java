package com.minlia.module.realname.event;

import com.minlia.module.riskcontrol.event.Event;
import lombok.Getter;
import lombok.Setter;

/**
 * @author garen
 */
@Setter
@Getter
public class RiskRealNameEvent extends Event {

    public RiskRealNameEvent(Long uid) {
        super();
        this.setScene("REALNAME_NUM");
        this.setSceneValue(uid.toString());
    }

}