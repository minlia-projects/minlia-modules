package com.minlia.module.attachment.v1;

import com.minlia.boot.v1.body.StatefulBody;
import com.minlia.boot.v1.endpoint.AbstractApiEndpoint;
import com.minlia.boot.v1.service.IService;
import com.minlia.boot.v1.web.ApiPrefix;
import com.minlia.module.attachment.v1.domain.Attachment;
import com.minlia.module.attachment.v1.service.AttachmentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.enterprise.inject.Produces;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by will on 6/21/17.
 */
@RestController
@RequestMapping(value = ApiPrefix.V1 + "attachments")
@Api(tags = "附件", description = "附件")
@Slf4j
public class AttachmentEndpoint extends AbstractApiEndpoint<Attachment>{

    @Override
    protected IService<Attachment> getService() {
        return attachmentService;
    }

    @Autowired
    AttachmentService attachmentService;

    /**
     * 创建
     * @return
     */
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "创建", notes = "创建", httpMethod = "POST", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "create", method = RequestMethod.POST, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody create(@Valid @RequestBody Attachment body) {
       return super.create(body);
    }

    /**
     * 更新
     */
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "更新", notes = "更新", httpMethod = "PUT", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "update", method = RequestMethod.PUT, consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody update(@Valid @RequestBody Attachment body) {
        return super.update(body.getId(),body);
    }

    /**
     * 删除
     */
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE, produces = {MediaType.APPLICATION_JSON_VALUE})
    public StatefulBody delete(@PathVariable Long id) {
        return super.delete(id);
    }

}
