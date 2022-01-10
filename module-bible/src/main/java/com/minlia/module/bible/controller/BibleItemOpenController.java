package com.minlia.module.bible.controller;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.bible.bean.BibleItemCro;
import com.minlia.module.bible.bean.BibleItemQro;
import com.minlia.module.bible.bean.BibleItemUro;
import com.minlia.module.bible.constant.BibleConstants;
import com.minlia.module.bible.entity.BibleItemEntity;
import com.minlia.module.bible.service.BibleItemService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 数据字典 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Api(tags = "System Bible Item", description = "数据字典")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "bible/item")
public class BibleItemOpenController {

    private final BibleItemService bibleItemService;

    public BibleItemOpenController(BibleItemService bibleItemService) {
        this.bibleItemService = bibleItemService;
    }

    @AuditLog(value = "query system bible item by body as list", type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "hasAnyAuthority('" + BibleConstants.SEARCH + "')")
    @ApiOperation(value = "查询集合")
    @PostMapping(value = "list")
    public Response queryList(@Valid @RequestBody BibleItemQro qro) {
        qro.setOpenFlag(true);
        return Response.success(bibleItemService.list(DozerUtils.map(qro, BibleItemEntity.class)));
    }

}