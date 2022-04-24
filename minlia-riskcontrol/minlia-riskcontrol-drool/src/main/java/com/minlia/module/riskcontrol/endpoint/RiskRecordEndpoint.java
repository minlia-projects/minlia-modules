package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskConstants;
import com.minlia.module.riskcontrol.service.RiskRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 风控-记录
 *
 * @author garen
 */
@Api(tags = "System Risk Record", description = "风控-记录")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/record")
@RequiredArgsConstructor
public class RiskRecordEndpoint {

    private RiskRecordService riskRecordService;

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskRecordService.queryById(id));
    }

}