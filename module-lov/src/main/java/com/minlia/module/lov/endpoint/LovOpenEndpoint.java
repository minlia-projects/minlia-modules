package com.minlia.module.lov.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.lov.bean.LovQRO;
import com.minlia.module.lov.servcie.LovService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "System Lov", description = "LOV值集")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "lov")
public class LovOpenEndpoint {

    @Autowired
    private LovService lovService;

    @AuditLog(value = "query a lov by id")
    @ApiOperation(value = "ID查询", httpMethod = "GET")
    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response one(@PathVariable Long id) {
        return Response.success(lovService.selectByPrimaryKey(id));
    }

    @AuditLog(value = "query lovs by query request body as list")
    @ApiOperation(value = "集合查询", httpMethod = "POST")
    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody LovQRO qro) {
        qro.setDisFlag(false);
        qro.setDelFlag(false);
        return Response.success(lovService.selectByAll(qro));
    }

    @AuditLog(value = "query lovs by query request body as paginated result")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response paginated(@RequestBody LovQRO qro) {
        qro.setDisFlag(false);
        qro.setDelFlag(false);
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> lovService.selectByAll(qro));
        return Response.success(pageInfo);
    }

}