package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.bean.AttachmentCTO;
import com.minlia.modules.attachment.bean.AttachmentQO;
import com.minlia.modules.attachment.bean.AttachmentUTO;
import com.minlia.modules.attachment.entity.Attachment;
import com.minlia.modules.attachment.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by will on 6/21/17.
 */
@Api(tags = "System Attachment", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "attachment")
public class AttachmentEndpoint {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AttachmentService attachmentService;

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response create(@Valid @RequestBody AttachmentCTO cto) {
        return Response.success(attachmentService.create(cto));
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response update(@Valid @RequestBody AttachmentUTO uto) {
        Attachment attachment = mapper.map(uto, Attachment.class);
        return Response.success(attachmentService.update(attachment));
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response delete(@PathVariable Long id) {
        return attachmentService.delete(id);
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryOne(@PathVariable Long id) {
        return Response.success(attachmentService.queryById(id));
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "业务查询", notes = "业务查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "fbb/{relationId}/{belongsTo}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryAllByRelationIdAndBelongsTo(@PathVariable String relationId, @PathVariable String belongsTo) {
        return Response.success(attachmentService.queryList(AttachmentQO.builder().relationId(relationId).belongsTo(belongsTo).build()));
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody AttachmentQO qo) {
        return Response.success(attachmentService.queryList(qo));
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response page(@PageableDefault Pageable pageable, @RequestBody AttachmentQO qo) {
        return Response.success(attachmentService.queryPage(qo, pageable));
    }

}
