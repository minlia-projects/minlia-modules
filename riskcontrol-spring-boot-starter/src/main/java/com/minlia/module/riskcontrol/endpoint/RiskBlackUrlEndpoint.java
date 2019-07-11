package com.minlia.module.riskcontrol.endpoint;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.riskcontrol.bean.RiskBlackUrlQRO;
import com.minlia.module.riskcontrol.entity.RiskBlackUrl;
import com.minlia.module.riskcontrol.mapper.RiskBlackUrlMapper;
import com.minlia.module.riskcontrol.service.RiskBlackUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = "System Risk Black Url", description = "风控-黑名单-URL")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "risk/black/url")
public class RiskBlackUrlEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private RiskBlackUrlService riskBlackUrlService;

    @Autowired
    private RiskBlackUrlMapper riskBlackUrlMapper;

    @ApiOperation(value = "保存")
    @PostMapping(value = "")
    public Response save(@Valid @RequestBody RiskBlackUrl riskBlackUrl) {
        riskBlackUrlService.pub(riskBlackUrl);
        return Response.success();
    }

    @ApiOperation(value = "重置")
    @PostMapping(value = "reset")
    public Response reset() {
        riskBlackUrlService.updateCache();
        return Response.success();
    }

    @ApiOperation(value = "ID删除")
    @DeleteMapping(value = "{id}")
    public Response delete(@PathVariable Long id) {
        riskBlackUrlService.delete(id);
        return Response.success();
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(path = "{id}")
    public Response queryById(@PathVariable Long id) {
        return Response.success(riskBlackUrlService.queryById(id));
    }

    @ApiOperation(value = "查询所有")
    @GetMapping(path = "all")
    public Response all() {
        return Response.success(riskBlackUrlService.getAll());
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(path = "page")
    public Response page(@RequestBody RiskBlackUrlQRO qro) {
        PageInfo pageInfo = PageHelper.startPage(qro.getPageNumber(), qro.getPageSize()).doSelectPageInfo(() -> riskBlackUrlMapper.selectByAll(mapper.map(qro, RiskBlackUrl.class)));
        return Response.success(pageInfo);
    }

}
