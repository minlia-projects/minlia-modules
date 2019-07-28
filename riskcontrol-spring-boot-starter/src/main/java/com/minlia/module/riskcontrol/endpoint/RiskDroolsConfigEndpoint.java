package com.minlia.module.riskcontrol.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.riskcontrol.bean.RiskDroolsConfigQRO;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
import com.minlia.module.riskcontrol.mapper.RiskDroolsConfigMapper;
import com.minlia.module.riskcontrol.service.RiskDroolsConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Drools Config", description = "风控-引擎规则配置")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/drools/config")
public class RiskDroolsConfigEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskDroolsConfigService riskDroolsConfigService;

    @Autowired
    private RiskDroolsConfigMapper riskDroolsConfigMapper;

    @AuditLog(value = "save fraud drools config", type = OperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.DROOLS_CONFIG_SAVE + "')")
    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskDroolsConfig riskDroolsConfig) {
        riskDroolsConfigService.pub(riskDroolsConfig);
        return Response.success();
    }

    @AuditLog(value = "reset drools config", type = OperationTypeEnum.MODIFY)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.DROOLS_CONFIG_RESET + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskDroolsConfigService.updateCache();
        return Response.success();
    }

    @AuditLog(value = "query fraud drools config by id", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.DROOLS_CONFIG_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{key}")
    public Response queryById(@PathVariable String key) {
        return Response.success(riskDroolsConfigService.get(key));
    }

    @AuditLog(value = "query fraud drools config as list", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.DROOLS_CONFIG_SEARCH + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskDroolsConfigService.getAll());
    }

    @AuditLog(value = "query fraud drools config as paginated", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.DROOLS_CONFIG_SEARCH + "')")
    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskDroolsConfigQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskDroolsConfigMapper.queryByAll(mapper.map(qro, RiskDroolsConfig.class)));
        return Response.success(pageInfo);
    }

//    @ApiOperation(value = "ID删除")
//    @DeleteMapping(value = "{id}")
//    public Response delete(@PathVariable Long id) {
//        riskConfigService.delete(id);
//        return Response.success();
//    }

}
