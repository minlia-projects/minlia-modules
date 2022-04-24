package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskConstants;
import com.minlia.module.riskcontrol.entity.RiskList;
import com.minlia.module.riskcontrol.service.RiskListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 风控-名单
 *
 * @author garen
 */
@Api(tags = "System Risk List", description = "风控-名单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/list")
@RequiredArgsConstructor
public class RiskListEndpoint {

    private final RiskListService riskListService;

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.CREATE + "')")
    @ApiOperation(value = "保存")
    @PostMapping
    public Response save(@Valid @RequestBody RiskList riskList) {
        riskListService.save(riskList);
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskListService.delete(id);
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskListService.findById(id));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.SELECT + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskListService.findAll());
    }

}