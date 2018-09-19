package com.minlia.module.todo.endpoint;

import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.todo.body.TodoCreateRequestBody;
import com.minlia.module.todo.body.TodoOperateRequestBody;
import com.minlia.module.todo.body.TodoQueryRequestBody;
import com.minlia.module.todo.body.TodoUpdateRequestBody;
import com.minlia.module.todo.constant.TodoSecurityConstants;
import com.minlia.module.todo.entity.MyTodo;
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
 * Created by will on 6/21/17.
 */
@Api(tags = "System Todo", description = "待办")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "todo")
public class TodoEndpoint {

    @Autowired
    private TodoService bibleService;

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.CREATE + "')")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody TodoCreateRequestBody body) {
        return Response.success(bibleService.create(body));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.UPDATE + "')")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody TodoUpdateRequestBody body) {
        return Response.success(bibleService.update(body));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.UPDATE + "')")
    @ApiOperation(value = "操作", notes = "操作", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "operate", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response operate(@Valid @RequestBody TodoOperateRequestBody body) {
        return Response.success(bibleService.operate(body));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.DELETE + "')")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{number}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable String number) {
        bibleService.delete(number);
        return Response.success();
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.SEARCH + "')")
    @ApiOperation(value = "编号查询", notes = "编号查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{number}", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryByNumber(@PathVariable String number) {
        return Response.success(bibleService.queryByNumber(number));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.SEARCH + "')")
    @ApiOperation(value = "集合查询", notes = "查询集合", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryList(@Valid @RequestBody TodoQueryRequestBody body) {
        return Response.success(bibleService.queryList(body));
    }

    @PreAuthorize(value = "hasAnyAuthority('" + TodoSecurityConstants.SEARCH + "')")
    @ApiOperation(value = "分页查询", notes = "查询分页", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryPage(@PageableDefault Pageable pageable, @RequestBody TodoQueryRequestBody body) {
        PageInfo<MyTodo> pageInfo = bibleService.queryPage(body,pageable);
        return Response.success(pageInfo);
    }

}

