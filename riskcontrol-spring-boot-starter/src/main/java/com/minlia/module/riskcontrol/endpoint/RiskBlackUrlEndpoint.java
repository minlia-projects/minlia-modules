package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.entity.RiskBlackUrl;
import com.minlia.module.riskcontrol.mapper.RiskBlackUrlMapper;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Url List", description = "风控-URL List")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/url/list")
public class RiskBlackUrlEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskBlackUrlService riskBlackUrlService;

    @Autowired
    private RiskBlackUrlMapper riskBlackUrlMapper;

    @AuditLog(value = "save fraud url list", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SAVE + "')")
    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskBlackUrl riskBlackUrl) {
        riskBlackUrlService.pub(riskBlackUrl);
        return Response.success();
    }

    @AuditLog(value = "reset black url list", type = AuditOperationTypeEnum.UPDATE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_RESET + "')")
    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskBlackUrlService.updateCache();
        return Response.success();
    }

    @AuditLog(value = "delete fraud url list by id", type = AuditOperationTypeEnum.DELETE)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_DELETE + "')")
    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskBlackUrlService.delete(id);
        return Response.success();
    }

    @AuditLog(value = "query fraud url list by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskBlackUrlService.queryById(id));
    }

    @AuditLog(value = "query fraud url list ", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SEARCH + "')")
    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskBlackUrlService.getAll());
    }

//    @AuditLog(value = "query fraud url list as paginated", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.URL_LIST_SEARCH + "')")
//    @ApiOperation(value = "分页查询")
//    @PostMapping(path = "page")
//    public Response page(@RequestBody RiskBlackUrlQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskBlackUrlMapper.selectByAll(mapper.map(qro, RiskBlackUrl.class)));
//        return Response.success(pageInfo);
//    }

}
