package com.minlia.module.audit.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.bean.AuditLogInfoQRO;
import com.minlia.module.audit.constant.AuditConstants;
import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.audit.service.AuditLogInfoService;
import com.minlia.modules.security.context.MinliaSecurityContextHolder;
import com.minlia.modules.security.model.UserContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = "System Audit Log", description = "审计日志")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "/audit", produces = {MediaType.APPLICATION_JSON_VALUE})
public class AuditLogEndpoint {

    @Autowired
    private AuditLogInfoService auditLogInfoService;

    @PreAuthorize(value = "hasAnyAuthority('" + AuditConstants.SEARCH + "')")
    @AuditLog(value = "query audit log as paginated result", type = OperationTypeEnum.INFO)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@RequestBody AuditLogInfoQRO qro) {
        return Response.success(auditLogInfoService.queryPage(qro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + AuditConstants.READ + "')")
    @AuditLog(value = "query audit log as paginated result", type = OperationTypeEnum.INFO)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page/me")
    public Response pageMe(@RequestBody AuditLogInfoQRO qro) {
        qro.setCreateBy(MinliaSecurityContextHolder.getCurrentGuid());
        return Response.success(auditLogInfoService.queryPage(qro));
    }

}
