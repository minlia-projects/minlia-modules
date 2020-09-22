package com.minlia.module.advertisement.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.advertisement.bean.SysAdvertisementsQro;
import com.minlia.module.advertisement.entity.SysAdvertisementEntity;
import com.minlia.module.advertisement.entity.SysAdvertisementsEntity;
import com.minlia.module.advertisement.service.SysAdvertisementService;
import com.minlia.module.advertisement.service.SysAdvertisementsService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * <p>
 * 广告 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-22
 */
@Api(tags = "System Advertisement Open", description = "广告")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "ad")
public class SysAdvertisementOpenController {

    private final SysAdvertisementService sysAdvertisementService;
    private final SysAdvertisementsService sysAdvertisementsService;

    public SysAdvertisementOpenController(SysAdvertisementService sysAdvertisementService, SysAdvertisementsService sysAdvertisementsService) {
        this.sysAdvertisementService = sysAdvertisementService;
        this.sysAdvertisementsService = sysAdvertisementsService;
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response queryList(@Valid @RequestBody SysAdvertisementsQro qro) {
        List<SysAdvertisementsEntity> advertisementsList = sysAdvertisementsService.list(Wrappers.<SysAdvertisementsEntity>lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysAdvertisementsEntity.class)).last(qro.getOrderBy()));

        for (SysAdvertisementsEntity advertisements : advertisementsList) {
            LambdaQueryWrapper queryWrapper = Wrappers.<SysAdvertisementEntity>lambdaQuery().eq(SysAdvertisementEntity::getAid, advertisements.getId());
            advertisements.setAdvertisements(sysAdvertisementService.list(queryWrapper));
        }
        return Response.success(advertisementsList);
    }

}