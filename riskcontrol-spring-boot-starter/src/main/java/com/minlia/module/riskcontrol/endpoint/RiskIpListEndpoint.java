package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskIpList;
import com.minlia.module.riskcontrol.mapper.RiskIpListMapper;
import com.minlia.module.riskcontrol.service.RiskIpListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Ip List", description = "风控-IP List")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/ip/list")
public class RiskIpListEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskIpListService riskIpListService;

    @Autowired
    private RiskIpListMapper riskIpListMapper;

    @AuditLog(value = "save fraud ip list", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SAVE + "')")
    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskIpList riskIpList) {
        riskIpListService.pub(riskIpList);
        return Response.success();
    }

    @AuditLog(value = "reset black ip list", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_RESET + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskIpListService.updateCache();
        return Response.success();
    }

    @AuditLog(value = "delete fraud ip list by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_DELETE + "')")
    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskIpListService.delete(id);
        return Response.success();
    }

    @AuditLog(value = "query fraud ip list by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskIpListService.queryById(id));
    }

    @AuditLog(value = "query fraud ip list by all", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SEARCH + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskIpListService.getAll());
    }

//    @AuditLog(value = "query fraud ip list as paginated", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.IP_LIST_SEARCH + "')")
//    @ApiOperation(value = "分页查询")
//    @PostMapping(path = "page")
//    public Response page(@RequestBody RiskIpListQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskIpListMapper.selectByAll(mapper.map(qro, RiskIpList.class)));
//        return Response.success(pageInfo);
//    }

}
