package com.minlia.module.approved.endpoint;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.approved.bean.ro.ApprovedQRO;
import com.minlia.module.approved.constant.ApprovedSecurityConstant;
import com.minlia.module.approved.service.ApprovedService;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "System Approved", description = "系统审批")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "security/approved")
public class ApprovedEndpoint {

    @Autowired
    private ApprovedService approvedService;

    @AuditLog(value = "query a approved", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_SEARCH + "')")
    @ApiOperation(value = "单个查询", notes = "单个查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "one", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response one(@RequestBody ApprovedQRO qro) {
        return Response.success(approvedService.selectDetailsOneByAll(qro));
    }

    @AuditLog(value = "query approved as paginated result", type = OperationTypeEnum.INFO)
    @PreAuthorize(value = "hasAnyAuthority('" + ApprovedSecurityConstant.APPROVED_SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "page", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response page(@RequestBody ApprovedQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> approvedService.selectDetailsByAll(qro));
//        pageInfo.setList(approvedService.selectDetailsByAll(pageInfo.getList()));
        return Response.success(pageInfo);
    }

}
