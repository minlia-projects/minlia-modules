package com.minlia.module.lov.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.lov.bean.LovQRO;
import com.minlia.module.lov.enntity.Lov;
import com.minlia.module.lov.servcie.LovService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Lov", description = "LOV值集")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "lov")
public class LovEndpoint {

    @Autowired
    private LovService lovService;

    @AuditLog(value = "create a lov")
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.create')")
    @ApiOperation(value = "创建", httpMethod = "POST")
    @PostMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody Lov lov) {
        return Response.success(lovService.insertSelective(lov));
    }

    @AuditLog(value = "update a lov")
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.update')")
    @ApiOperation(value = "更新", httpMethod = "PUT")
    @PutMapping(value = "", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody Lov lov) {
        return Response.success(lovService.updateByPrimaryKeySelective(lov));
    }

    @AuditLog(value = "toggle a lov status by id")
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.delete')")
    @ApiOperation(value = "启用/禁用", httpMethod = "DELETE")
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        return Response.success(lovService.disable(id));
    }

//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.search')")
//    @ApiOperation(value = "ID查询", httpMethod = "GET")
//    @GetMapping(value = "{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response one(@PathVariable Long id) {
//        return Response.success(lovService.selectByPrimaryKey(id));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.search')")
//    @ApiOperation(value = "集合查询", httpMethod = "POST")
//    @PostMapping(value = "list", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response list(@RequestBody LovQRO qro) {
//        return Response.success(lovService.selectByAll(qro));
//    }
//
//    @PreAuthorize(value = "hasAnyAuthority('minlia.lov.search')")
//    @ApiOperation(value = "分页查询", httpMethod = "POST")
//    @PostMapping(value = "page", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
//    public Response paginated(@RequestBody LovQRO qro) {
//        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> lovService.selectByAll(qro));
//        return Response.success(pageInfo);
//    }

}