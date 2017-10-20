package com.minlia.module.attachment.v1;

import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.attachment.v1.body.AttachmentQueryRequestBody;
import com.minlia.module.attachment.v1.domain.Attachment;
import com.minlia.module.attachment.v1.service.AttachmentReadOnlyService;
import com.minlia.module.attachment.v1.service.AttachmentWriteOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "attachments")
@Api(tags = "附件", description = "附件")
@Slf4j
public class AttachmentEndpoint {

    @Autowired
    private AttachmentWriteOnlyService attachmentWriteOnlyService;

    @Autowired
    private AttachmentReadOnlyService attachmentReadOnlyService;

    @PreAuthorize(value = "hasAnyAuthority('tenant.categories.create')")
    @ApiOperation(value = "添加附件", notes = "添加附件", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody create(@RequestBody Attachment body) {
        Attachment created=attachmentWriteOnlyService.save(body);
        return SuccessResponseBody.builder().payload(created).build();
    }


    @PreAuthorize(value = "hasAnyAuthority('tenant.categories.update')")
    @ApiOperation(value = "更新附件", notes = "更新附件", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
    @PutMapping(value = "update", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody update(@RequestBody Attachment body) {
        attachmentWriteOnlyService.update(body);
        return SuccessResponseBody.builder().payload(body).build();
    }

//    @PreAuthorize(value = "hasAnyAuthority('tenant.categories.batchUpdate')")
//    @ApiOperation(value = "批量更新附件", notes = "批量更新附件", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_VALUE)
//    @PutMapping(value = "batchUpdate", produces = MediaType.APPLICATION_JSON_VALUE)
//    public StatefulBody update(@RequestBody Set<Attachment> body) {
//        AttachmentWriteOnlyService.batchUpdate(body);
//        return SuccessResponseBody.builder().payload(body).build();
//    }

    @PreAuthorize(value = "hasAnyAuthority('tenant.categories.delete')")
    @ApiOperation(value = "删除附件", notes = "删除附件", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @DeleteMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody delete(@PathVariable Long id) {
        attachmentWriteOnlyService.delete(id);
        return SuccessResponseBody.builder().build();
    }

    @PreAuthorize(value = "hasAnyAuthority('tenant.categories.find')")
    @ApiOperation(value = "获取当前应用的所有附件, 不分页", notes = "获取当前应用的所有附件, 不分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @GetMapping(value = "findAllList", produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody findAllList() {
        List<Attachment> found = attachmentReadOnlyService.findAll();
        return SuccessResponseBody.builder().payload(found).build();
    }

}
