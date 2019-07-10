package com.minlia.module.riskcontrol.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.bean.RiskDroolsConfigQRO;
import com.minlia.module.riskcontrol.entity.RiskDroolsConfig;
import com.minlia.module.riskcontrol.mapper.RiskDroolsConfigMapper;
import com.minlia.module.riskcontrol.service.RiskDroolsConfigService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Drools Config", description = "风控-引擎规则配置")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "risk/drools/config")
public class RiskDroolsConfigEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskDroolsConfigService riskDroolsConfigService;

    @Autowired
    private RiskDroolsConfigMapper riskDroolsConfigMapper;

    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskDroolsConfigService.getAll());
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{key}")
    public Response queryById(@PathVariable String key) {
        return Response.success(riskDroolsConfigService.get(key));
    }

    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskDroolsConfig riskDroolsConfig) {
        riskDroolsConfigService.pub(riskDroolsConfig);
        return Response.success();
    }

    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskDroolsConfigService.updateCache();
        return Response.success();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskDroolsConfigQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> riskDroolsConfigMapper.queryByAll(mapper.map(qro, RiskDroolsConfig.class)));
        return Response.success(pageInfo);
    }

//    @ApiOperation(value = "ID删除")
//    @DeleteMapping(value = "{id}")
//    public Response delete(@PathVariable Long id) {
//        riskConfigService.delete(id);
//        return Response.success();
//    }

}