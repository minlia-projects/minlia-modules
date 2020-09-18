package com.minlia.module.riskcontrol.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.AuditOperationTypeEnum;
import com.minlia.module.riskcontrol.constant.RiskSecurityConstants;
import com.minlia.module.riskcontrol.mapper.RiskRecordMapper;
import com.minlia.module.riskcontrol.service.RiskRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Api(tags = "System Risk Record", description = "风控-记录")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "risk/record")
public class RiskRecordEndpoint {

    @Autowired
    private RiskRecordService riskRecordService;

    @Autowired
    private RiskRecordMapper riskRecordMapper;

//    @ApiOperation(value = "查询所有")
//    @GetMapping(path = "all")
//    public Response all() {
//        return Response.success(riskRecordService.queryAll());
//    }

    @AuditLog(value = "query fraud record by id", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.RECORD_SEARCH + "')")
    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskRecordService.queryById(id));
    }

//    @AuditLog(value = "query fraud record as paginated", type = AuditOperationTypeEnum.SELECT)
//    @PreAuthorize(value = "hasAnyAuthority('" + RiskSecurityConstants.RECORD_SEARCH + "')")
//    @ApiOperation(value = "分页查询")
//    @PostMapping(path = "page")
//    public Response page(@RequestBody RiskRecordQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> riskRecordMapper.selectByAll(qro));
//        return Response.success(pageInfo);
//    }

}
