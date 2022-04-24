package com.minlia.module.riskcontrol.listener;

import com.minlia.module.riskcontrol.entity.RiskList;
import com.minlia.module.riskcontrol.enums.RiskTypeEnum;
import com.minlia.module.riskcontrol.event.RiskListDeleteEvent;
import com.minlia.module.riskcontrol.event.RiskListPublishEvent;
import com.minlia.module.riskcontrol.service.RiskBlackIpService;
import com.minlia.module.riskcontrol.service.RiskListService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 风控发布事件监听
 *
 * @author garen
 */
@Component
@RequiredArgsConstructor
public class RiskListPublishListener {

    private final RiskListService riskListService;
    private final RiskBlackIpService riskBlackIpService;

    @EventListener
    public void onPublish(RiskListPublishEvent event) {
        RiskList riskList = (RiskList) event.getSource();
        if (riskList.getType() == RiskTypeEnum.BLACK && riskList.getDimension() == RiskList.EnumDimension.IP) {
            riskBlackIpService.publish(riskList.getValue());
        }
    }

    @EventListener
    public void onDelete(RiskListDeleteEvent event) {
        RiskList riskList = (RiskList) event.getSource();
        if (riskList.getType() == RiskTypeEnum.BLACK && riskList.getDimension() == RiskList.EnumDimension.IP) {
            riskBlackIpService.delete(riskList.getValue());
        }
    }

}