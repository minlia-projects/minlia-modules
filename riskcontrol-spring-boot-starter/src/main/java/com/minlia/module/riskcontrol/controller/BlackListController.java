package com.minlia.module.riskcontrol.controller;

import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.entity.BlackList;
import com.minlia.module.riskcontrol.service.BlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Black List", description = "黑名单")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "blacklist")
public class BlackListController {

    @Autowired
    private BlackListService blackListService;

    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(blackListService.getAll());
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(blackListService.queryById(id));
    }

    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody BlackList blackList) {
        blackListService.pub(blackList);
        return Response.success();
    }

    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        blackListService.delete(id);
        return Response.success();
    }

}
