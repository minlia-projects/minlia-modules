package com.minlia.module.lov.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.lov.bean.LovValueQRO;
import com.minlia.module.lov.enntity.LovValue;
import com.minlia.module.lov.servcie.LovValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@Api(tags = "System Lov Value", description = "LOV值")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "lov/value")
public class LovValueOpenEndpoint {

    @Autowired
    private LovValueService lovValueService;

    @AuditLog(value = "query a lov value by id")
    @ApiOperation(value = "ID查询", httpMethod = "GET")
    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response one(@PathVariable Long id) {
        return Response.success(lovValueService.selectByPrimaryKey(id));
    }

    @AuditLog(value = "query lov values by query request body as list")
    @ApiOperation(value = "集合查询", httpMethod = "POST")
    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody LovValueQRO qro) {
        qro.setDisFlag(false);
        qro.setDelFlag(false);
        qro.setLocale(LocaleContextHolder.getLocale().toString());
        return Response.success(lovValueService.selectByAll(qro));
    }

    @AuditLog(value = "query lov values by query request body as paginated result")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response paginated(@RequestBody LovValueQRO qro) {
        qro.setDisFlag(false);
        qro.setDelFlag(false);
        qro.setLocale(LocaleContextHolder.getLocale().toString());
        PageInfo<LovValue> pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> lovValueService.selectByAll(qro));
        return Response.success(pageInfo);
    }

}