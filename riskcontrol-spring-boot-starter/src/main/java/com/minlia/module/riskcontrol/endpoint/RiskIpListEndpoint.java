package com.minlia.module.riskcontrol.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.bean.RiskIpListQRO;
import com.minlia.module.riskcontrol.entity.RiskIpList;
import com.minlia.module.riskcontrol.mapper.RiskIpListMapper;
import com.minlia.module.riskcontrol.service.RiskIpListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Ip List", description = "风控-IP List")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "risk/ip/list")
public class RiskIpListEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskIpListService riskIpListService;

    @Autowired
    private RiskIpListMapper riskIpListMapper;

    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskIpList riskIpList) {
        riskIpListService.pub(riskIpList);
        return Response.success();
    }

    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskIpListService.updateCache();
        return Response.success();
    }

    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskIpListService.delete(id);
        return Response.success();
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskIpListService.queryById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskIpListService.getAll());
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskIpListQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> riskIpListMapper.selectByAll(mapper.map(qro, RiskIpList.class)));
        return Response.success(pageInfo);
    }

}
