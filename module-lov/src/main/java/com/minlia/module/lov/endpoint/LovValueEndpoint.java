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
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Lov Value", description = "LOV值")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "lov/value")
public class LovValueEndpoint {

    @Autowired
    private LovValueService lovValueService;

    @AuditLog(value = "create a lov value")
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.create')")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody LovValue lovValue) {
        return Response.success(lovValueService.insertSelective(lovValue));
    }

    @AuditLog(value = "update a lov value")
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.update')")
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody LovValue lovValue) {
        return Response.success(lovValueService.updateByPrimaryKeySelective(lovValue));
    }

    @AuditLog(value = "toggle a lov status by id")
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.delete')")
    @ApiOperation(value = "启用/禁用", httpMethod = "DELETE")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        return Response.success(lovValueService.disable(id));
    }

    //    @PreAuthorize(value = "hasAnyAuthority('minlia.lov_value.search')")
//    @ApiOperation(value = "ID查询", httpMethod = "GET")
//    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response one(@PathVariable Long id) {
//        return Response.success(lovValueService.selectByPrimaryKey(id));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov_value.search')")
//    @ApiOperation(value = "集合查询", httpMethod = "POST")
//    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@RequestBody LovValueQRO qro) {
//        return Response.success(lovValueService.selectByAll(qro));
//    }
//
    @PreAuthorize(value = "hasAnyAuthority('system.lov_value.search')")
    @ApiOperation(value = "分页查询", httpMethod = "POST")
    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response paginated(@RequestBody LovValueQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize(), qro.getOrderBy()).doSelectPageInfo(() -> lovValueService.selectByAll(qro));
        return Response.success(pageInfo);
    }

}