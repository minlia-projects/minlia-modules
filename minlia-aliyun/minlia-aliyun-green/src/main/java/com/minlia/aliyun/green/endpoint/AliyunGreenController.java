package com.minlia.aliyun.green.endpoint;


import com.minlia.aliyun.green.bean.AliyunGreenContentCro;
import com.minlia.aliyun.green.bean.AliyunGreenImageCro;
import com.minlia.aliyun.green.service.AliyunGreenContentService;
import com.minlia.aliyun.green.service.AliyunGreenImageService;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 内容审核 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Api(tags = "Aliyun Green")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "aliyun/sts")
@RequiredArgsConstructor
public class AliyunGreenController {

    private final AliyunGreenImageService aliyunGreenImageService;
    private final AliyunGreenContentService aliyunGreenContentService;

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "文本审核")
    @PostMapping(value = "content")
    public Response getCredentials(@Validated @RequestBody AliyunGreenContentCro cro) {
        return Response.success(aliyunGreenContentService.handle(cro.getContent()));
    }

    @AuditLog(type = AuditOperationTypeEnum.CREATE)
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "图片审核")
    @PostMapping(value = "image")
    public Response getCredentials(@Validated @RequestBody AliyunGreenImageCro cro) {
        return Response.success(aliyunGreenImageService.handle(cro.getUrl()));
    }

}