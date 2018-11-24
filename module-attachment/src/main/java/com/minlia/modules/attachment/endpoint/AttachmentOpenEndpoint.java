package com.minlia.modules.attachment.endpoint;


import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.modules.attachment.bean.AttachmentQO;
import com.minlia.modules.attachment.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Created by garen on 2017/6/28.
 */
@Api(tags = "System Attachment Open", description = "附件")
@RestController
@RequestMapping(value = ApiPrefix.OPEN + "attachment")
public class AttachmentOpenEndpoint {

    @Autowired
    private AttachmentService attachmentService;

    @ApiOperation(value = "ID查询", notes = "ID查询", httpMethod = "GET", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.GET, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response queryOne(@PathVariable Long id) {
        return Response.success(attachmentService.queryById(id));
    }

    @ApiOperation(value = "集合查询", notes = "集合查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response list(@RequestBody AttachmentQO qo) {
        return Response.success(attachmentService.queryList(qo));
    }

    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "page", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Response page(@PageableDefault Pageable pageable,@RequestBody AttachmentQO qo) {
        return Response.success(attachmentService.queryPage(qo, pageable));
    }

}
