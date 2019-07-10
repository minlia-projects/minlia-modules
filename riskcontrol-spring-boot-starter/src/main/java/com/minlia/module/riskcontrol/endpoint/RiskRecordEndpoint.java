package com.minlia.module.riskcontrol.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.bean.RiskRecordQRO;
import com.minlia.module.riskcontrol.entity.RiskRecord;
import com.minlia.module.riskcontrol.mapper.RiskRecordMapper;
import com.minlia.module.riskcontrol.service.RiskRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "System Risk Record", description = "风控-记录")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "risk/record")
public class RiskRecordEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskRecordService riskRecordService;

    @Autowired
    private RiskRecordMapper riskRecordMapper;

    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskRecordService.queryAll());
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskRecordService.queryById(id));
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskRecordQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> riskRecordMapper.selectByAll(mapper.map(qro, RiskRecord.class)));
        return Response.success(pageInfo);
    }

//    @ApiOperation(value = "ID删除")
//    @DeleteMapping(value = "{id}")
//    public Response delete(@PathVariable Long id) {
//        riskRecordService.delete(id);
//        return Response.success();
//    }

}