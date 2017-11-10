package com.minlia.module.attachment.v1;


import com.minlia.cloud.body.StatefulBody;
import com.minlia.cloud.body.impl.SuccessResponseBody;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.cloud.query.specification.batis.body.ApiQueryRequestBody;
import com.minlia.module.attachment.v1.body.AttachmentQueryRequestBody;
import com.minlia.module.attachment.v1.service.AttachmentReadOnlyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = ApiPrefix.V1 + "attachment/search")
@Api(tags = "附件", description = "附件")
public class AttachmentSearchEndpoint {

    @Autowired
    private AttachmentReadOnlyService attachmentReadOnlyService;

    @PreAuthorize(value = "isAuthenticated()")
    @RequestMapping(value = "", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ApiOperation(value = "分页查询", notes = "分页查询", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public StatefulBody findAll(@PageableDefault Pageable pageable,@RequestBody ApiQueryRequestBody<AttachmentQueryRequestBody> body) {
        Page x = attachmentReadOnlyService.findAll(body,pageable);
        return SuccessResponseBody.builder().payload(x).build();
    }


}