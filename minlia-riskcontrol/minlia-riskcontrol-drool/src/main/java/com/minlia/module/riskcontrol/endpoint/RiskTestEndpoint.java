package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.event.RiskBlackIpEvent;
import com.minlia.module.riskcontrol.service.RiskKieService;
import com.minlia.module.riskcontrol.service.RiskListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author garen
 */
@Api(tags = "System Risk Test", description = "风控-测试")
@RestController
@RequestMapping(value = ApiPrefix.API + "risk/test")
@RequiredArgsConstructor
public class RiskTestEndpoint {

    private final RiskListService riskListService;

    @AuditLog(value = "query fraud black list by id", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "测试")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        RiskBlackIpEvent event = new RiskBlackIpEvent();
        event.setIp(id.toString());
        RiskKieService.execute(event);
        return Response.success();
    }

}