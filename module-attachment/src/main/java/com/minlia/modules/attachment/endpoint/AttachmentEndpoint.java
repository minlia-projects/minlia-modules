package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.body.AttachmentCreateRequestBody;
import com.minlia.modules.attachment.body.AttachmentQueryRequestBody;
import com.minlia.modules.attachment.body.AttachmentUpdateRequestBody;
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
    public StatefulBody create(@Valid @RequestBody AttachmentCreateRequestBody body) {
        return SuccessResponseBody.builder().payload(attachmentService.create(body)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody AttachmentUpdateRequestBody requestBody) {
        Attachment attachment = mapper.map(requestBody,Attachment.class);
        return SuccessResponseBody.builder().payload(attachmentService.update(attachment)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        return attachmentService.delete(id);
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryOne(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(attachmentService.queryById(id)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "业务查询", notes = "业务查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "fbb/{relationId}/{belongsTo}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryAllByRelationIdAndBelongsTo(@PathVariable String relationId, @PathVariable String belongsTo) {
        return SuccessResponseBody.builder().payload(attachmentService.queryAllByRelationIdAndBelongsTo(relationId,belongsTo)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@RequestBody AttachmentQueryRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(attachmentService.queryList(requestBody)).build();
    }

    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody page(@PageableDefault Pageable pageable, @RequestBody AttachmentQueryRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(attachmentService.queryPage(requestBody,pageable)).build();
    }

}
