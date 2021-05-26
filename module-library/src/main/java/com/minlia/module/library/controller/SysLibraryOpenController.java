package com.minlia.module.library.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.dozer.util.DozerUtils;
import com.minlia.module.library.bean.SysLibraryKeywordQro;
import com.minlia.module.library.bean.SysLibraryQro;
import com.minlia.module.library.bean.SysLibraryVo;
import com.minlia.module.library.entity.SysLibraryEntity;
import com.minlia.module.library.service.SysLibraryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 文库 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Api(tags = "System Library Open", description = "文库")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "/library")
public class SysLibraryOpenController {

    private final SysLibraryService sysLibraryService;

    public SysLibraryOpenController(SysLibraryService sysLibraryService) {
        this.sysLibraryService = sysLibraryService;
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "关键字")
    @PostMapping(value = "keyword")
    public Response keyword(@Valid @RequestBody SysLibraryKeywordQro qro) {
        LambdaQueryWrapper<SysLibraryEntity> queryWrapper = new QueryWrapper<SysLibraryEntity>().lambda()
                .select(SysLibraryEntity::getId, SysLibraryEntity::getName, SysLibraryEntity::getUrl, SysLibraryEntity::getSummary)
                .eq(SysLibraryEntity::getDisFlag, false);
        if (StringUtils.isNotBlank(qro.getKeyword())) {
            queryWrapper.like(SysLibraryEntity::getKeyword, qro.getKeyword());
            queryWrapper.or().like(SysLibraryEntity::getName, qro.getKeyword());
            queryWrapper.or().like(SysLibraryEntity::getContent, qro.getKeyword());
        }
        Page page = sysLibraryService.page(new Page(qro.getPageNumber(), qro.getPageSize()), queryWrapper);
        page.setRecords(DozerUtils.map(page.getRecords(), SysLibraryVo.class));
        return Response.success(page);
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@Valid @RequestBody SysLibraryQro qro) {
        qro.setDisFlag(false);
        return Response.success(sysLibraryService.list(sysLibraryService.builderQueryWrapper(qro)));
    }

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @ApiOperation(value = "分页查询")
    @PostMapping(value = "page")
    public Response page(@Valid @RequestBody SysLibraryQro qro) {
        qro.setDisFlag(false);
        Page page = sysLibraryService.page(qro.getPage(), sysLibraryService.builderQueryWrapper(qro));
        page.setRecords(DozerUtils.map(page.getRecords(), SysLibraryVo.class));
        return Response.success(page);
    }

}