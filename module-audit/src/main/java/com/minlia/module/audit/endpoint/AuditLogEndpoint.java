package com.minlia.module.audit.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.bean.AuditLogInfoQRO;
import com.minlia.module.audit.service.AuditLogInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@RequestBody AuditLogInfoQRO qro) {
        return Response.success(auditLogInfoService.queryPage(qro));
    }

}
