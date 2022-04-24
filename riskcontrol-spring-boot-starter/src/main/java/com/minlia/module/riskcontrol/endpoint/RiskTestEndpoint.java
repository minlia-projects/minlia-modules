package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskBlackList;
import com.minlia.module.riskcontrol.event.RiskLoginEvent;
import com.minlia.module.riskcontrol.mapper.RiskBlackListMapper;
import com.minlia.module.riskcontrol.service.KieService;
import com.minlia.module.riskcontrol.service.RiskBlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Test", description = "风控-测试")
@RestController
@RequestMapping(value = ApiPrefix.API + "risk/test")
public class RiskTestEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskBlackListService riskBlackListService;

    @Autowired
    private RiskBlackListMapper riskBlackListMapper;

    @AuditLog(value = "query fraud black list by id", type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "测试")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        RiskLoginEvent event = new RiskLoginEvent("111111");
        event.setIp(id.toString());
        KieService.execute(event);
        return Response.success();
    }

}
