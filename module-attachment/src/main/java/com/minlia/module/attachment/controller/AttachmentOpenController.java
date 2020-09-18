package com.minlia.module.attachment.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.attachment.entity.SysAttachmentEntity;
import com.minlia.module.attachment.bean.AttachmentQro;
import com.minlia.module.attachment.service.AttachmentService;
import com.minlia.module.dozer.util.DozerUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 附件 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Api(tags = "System Attachment Open", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "attachment")
public class AttachmentOpenController {

    private final AttachmentService attachmentService;

    public AttachmentOpenController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @ApiOperation(value = "ID查询")
    @GetMapping(value = "{id}")
    public Response queryOne(@PathVariable Long id) {
        return Response.success(attachmentService.getById(id));
    }

    @ApiOperation(value = "集合查询")
    @PostMapping(value = "list")
    public Response list(@RequestBody AttachmentQro qro) {
        return Response.success(attachmentService.lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysAttachmentEntity.class)).last(qro.getOrderBy())
                .list());
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "page")
    public Response page(@RequestBody AttachmentQro qro) {
        return Response.success(attachmentService.lambdaQuery()
                .setEntity(DozerUtils.map(qro, SysAttachmentEntity.class)).last(qro.getOrderBy())
                .page(new Page<>(qro.getPageNumber(), qro.getPageSize())));
    }

}