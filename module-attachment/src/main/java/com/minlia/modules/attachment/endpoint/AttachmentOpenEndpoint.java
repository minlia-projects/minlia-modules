package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.body.AttachmentQueryRequestBody;
import com.minlia.modules.attachment.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by will on 6/21/17.
 */
@Api(tags = "System Attachment Open", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "attachment")
public class AttachmentOpenEndpoint {

    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryOne(@PathVariable Long id) {
        return SuccessResponseBody.builder().payload(attachmentService.queryById(id)).build();
    }

    @ApiOperation(value = "业务查询", notes = "业务查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "fbb/{relationId}/{belongsTo}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody queryAllByRelationIdAndBelongsTo(@PathVariable("relationId") String relationId, @PathVariable("belongsTo") String belongsTo) {
        return SuccessResponseBody.builder().payload(attachmentService.queryAllByRelationIdAndBelongsTo(relationId,belongsTo)).build();
    }

    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody list(@RequestBody AttachmentQueryRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(attachmentService.queryList(requestBody)).build();
    }

    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody page(@PageableDefault Pageable pageable,@RequestBody AttachmentQueryRequestBody requestBody) {
        return SuccessResponseBody.builder().payload(attachmentService.queryPage(requestBody,pageable)).build();
    }

}
