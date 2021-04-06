package com.minlia.module.district.controller;


import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.district.bean.DistrictQro;
import com.minlia.module.district.entity.SysDistrictEntity;
import com.minlia.module.district.enumeration.DistrictLevel;
import com.minlia.module.district.service.SysDistrictService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 区域 前端控制器
 * </p>
 *
 * @author garen
 * @since 2021-04-06
 */
@Api(tags = "System district", description = "区域")
@RestController
@RequestMapping(value = ApiPrefix.API + "district")
@RequiredArgsConstructor
public class SysDistrictController {

    private final SysDistrictService sysDistrictService;

//    @ApiOperation(value = "初始化")
//    @GetMapping(value = "init")
//    public Response init() {
//        return Response.success(sysDistrictService.init());
//    }

    @ApiOperation(value = "查询级别")
    @GetMapping(value = "level/{level}")
    public Response queryAllByParentCode(@PathVariable DistrictLevel level) {
        return Response.success(sysDistrictService.list(Wrappers.<SysDistrictEntity>lambdaQuery()
                .eq(SysDistrictEntity::getLevel, level)));
    }

    @ApiOperation(value = "查询子级")
    @GetMapping(value = "children")
    public Response queryAllByParentCode(@RequestParam(required = false) String parent) {
        return Response.success(sysDistrictService.list(Wrappers.<SysDistrictEntity>lambdaQuery()
                .eq(SysDistrictEntity::getParent, StringUtils.isBlank(parent) ? DistrictLevel.country : parent)));
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody DistrictQro qro) {
        return Response.success(sysDistrictService.list(Wrappers.<SysDistrictEntity>lambdaQuery().setEntity(DozerUtils.map(qro, SysDistrictEntity.class))));
    }

}