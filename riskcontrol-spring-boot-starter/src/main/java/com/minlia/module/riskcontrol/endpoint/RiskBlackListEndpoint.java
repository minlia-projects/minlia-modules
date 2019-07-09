package com.minlia.module.riskcontrol.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.bean.RiskBlackListQRO;
import com.minlia.module.riskcontrol.entity.RiskBlackList;
import com.minlia.module.riskcontrol.mapper.RiskBlackListMapper;
import com.minlia.module.riskcontrol.service.RiskBlackListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Black List", description = "风控-黑名单")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "risk/blacklist")
public class RiskBlackListEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskBlackListService riskBlackListService;

    @Autowired
    private RiskBlackListMapper riskBlackListMapper;

    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskBlackList riskBlackList) {
        riskBlackListService.pub(riskBlackList);
        return Response.success();
    }

    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskBlackListService.updateCache();
        return Response.success();
    }

    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskBlackListService.delete(id);
        return Response.success();
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskBlackListService.queryById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskBlackListService.getAll());
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskBlackListQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> riskBlackListMapper.selectByAll(mapper.map(qro, RiskBlackList.class)));
        return Response.success(pageInfo);
    }

}
