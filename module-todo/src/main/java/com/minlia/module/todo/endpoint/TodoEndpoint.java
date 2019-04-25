package com.minlia.module.todo.endpoint;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.todo.bean.qo.TodoQO;
import com.minlia.module.todo.bean.to.TodoCTO;
import com.minlia.module.todo.bean.to.TodoOperateTO;
import com.minlia.module.todo.bean.to.TodoUTO;
import com.minlia.module.todo.constant.TodoConstants;
import com.minlia.module.todo.service.TodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by garen on 2018/6/21.
 */
@Api(tags = "System Todo", description = "待办")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "todo")
public class TodoEndpoint {

    @Autowired
    private TodoService bibleService;

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody TodoCTO cto) {
        return Response.success(bibleService.create(cto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody TodoUTO uto) {
        return Response.success(bibleService.update(uto));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.UPDATE + "')")
    @ApiOperation(value = "操作", notes = "操作", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "operate", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response operate(@Valid @RequestBody TodoOperateTO to) {
        return Response.success(bibleService.operate(to));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{number}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable String number) {
        bibleService.delete(number);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.SEARCH + "')")
    @ApiOperation(value = "编号查询", notes = "编号查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{number}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByNumber(@PathVariable String number) {
        return Response.success(bibleService.queryByNumber(number));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "查询集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryList(@Valid @RequestBody TodoQO qro) {
        return Response.success(bibleService.queryList(qro));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "查询分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPage(@PageableDefault Pageable pageable, @RequestBody TodoQO qro) {
        return Response.success(bibleService.queryPage(qro, pageable));
    }

}

