package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskBlackList;
import com.minlia.module.riskcontrol.mapper.RiskBlackListMapper;
import com.minlia.module.riskcontrol.service.RiskBlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Black List", description = "风控-黑名单")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/black/list")
public class RiskBlackListEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskBlackListService riskBlackListService;

    @Autowired
    private RiskBlackListMapper riskBlackListMapper;

    @AuditLog(value = "save fraud black list", type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.BLACK_LIST_SAVE + "')")
    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskBlackList riskBlackList) {
        riskBlackListService.pub(riskBlackList);
        return Response.success();
    }

    @AuditLog(value = "reset fraud black list", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.BLACK_LIST_RESET + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskBlackListService.updateCache();
        return Response.success();
    }

    @AuditLog(value = "delete fraud black list by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.BLACK_LIST_DELETE + "')")
    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskBlackListService.delete(id);
        return Response.success();
    }

    @AuditLog(value = "query fraud black list by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.BLACK_LIST_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskBlackListService.queryById(id));
    }

    @AuditLog(value = "query fraud black list by all", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.BLACK_LIST_SEARCH + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskBlackListService.getAll());
    }

//    @AuditLog(value = "query fraud black list as paginated", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.BLACK_LIST_SEARCH + "')")
//    @ApiOperation(value = "分页查询")
//    @PostMapping(path = "page")
//    public Response page(@RequestBody RiskBlackListQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskBlackListMapper.selectByAll(mapper.map(qro, RiskBlackList.class)));
//        return Response.success(pageInfo);
//    }

}
