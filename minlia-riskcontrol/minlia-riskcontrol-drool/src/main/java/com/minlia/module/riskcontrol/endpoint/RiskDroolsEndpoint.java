package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskConstants;
import com.minlia.module.riskcontrol.entity.RiskDrools;
import com.minlia.module.riskcontrol.service.RiskDroolsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 风控-规则
 *
 * @author garen
 */
@Api(tags = "System Risk Drools", description = "风控-规则")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/drools")
@RequiredArgsConstructor
public class RiskDroolsEndpoint {

    private final RiskDroolsService riskDroolsService;

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.CREATE + "')")
    @ApiOperation(value = "发布")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskDrools riskDrools) {
        riskDroolsService.publish(riskDrools);
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.CREATE + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskDroolsService.reset();
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.DELETE + "')")
    @ApiOperation(value = "删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskDroolsService.delete(id);
        return Response.success();
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.SELECT + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{key}")
    public Response queryById(@PathVariable String key) {
        return Response.success(riskDroolsService.get(key));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskConstants.Authority.SELECT + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskDroolsService.findAll(RiskDrools.builder().build()));
    }

//    @AuditLog(value = "query fraud drools config as paginated", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.DROOLS_CONFIG_SEARCH + "')")
//    @ApiOperation(value = "分页查询")
//    @PostMapping(path = "page")
//    public Response page(@RequestBody RiskDroolsConfigQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskDroolsConfigMapper.queryByAll(mapper.map(qro, RiskDroolsConfig.class)));
//        return Response.success(pageInfo);
//    }

}
