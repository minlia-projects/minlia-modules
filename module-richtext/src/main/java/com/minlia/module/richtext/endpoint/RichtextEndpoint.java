package com.minlia.module.richtext.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.richtext.bean.RichtextCRO;
import com.minlia.module.richtext.bean.RichtextQRO;
import com.minlia.module.richtext.bean.RichtextURO;
import com.minlia.module.richtext.constant.RichtextConstants;
import com.minlia.module.richtext.service.RichtextService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Rich Text", description = "富文本")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "richtext")
public class RichtextEndpoint {

    @Autowired
    private RichtextService richtextService;

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextConstants.CREATE+"')")
    @ApiOperation(value = "create")
    @PostMapping(value = "create")
    public Response create(@Valid @RequestBody RichtextCRO cto) {
        return Response.success(richtextService.create(cto));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextConstants.UPDATE+"')")
    @ApiOperation(value = "update")
    @PutMapping(value = "update")
    public Response update(@Valid @RequestBody RichtextURO uto) {
        return Response.success(richtextService.update(uto));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextConstants.DELETE+"')")
    @ApiOperation(value = "delete")
    @DeleteMapping(value = "delete/{id}")
    public Response update(@PathVariable Long id) {
        richtextService.delete(id);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "id")
    @GetMapping(value = "{id}")
    public Response findOne(@PathVariable Long id) {
        return Response.success(richtextService.queryById(id));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "list")
    @PostMapping(value = "list")
    public Response list(@RequestBody RichtextQRO qro) {
        return Response.success(richtextService.queryList(qro));
    }

    @PreAuthorize(value = "hasAnyAuthority('"+ RichtextConstants.SEARCH + "')")
    @ApiOperation(value = "page")
    @PostMapping(value = "page")
    public Response page(@RequestBody RichtextQRO qro) {
        return Response.success(richtextService.queryPage(qro));
    }

}