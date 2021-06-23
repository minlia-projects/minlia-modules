package com.minlia.aliyun.sts.endpoint;


import com.minlia.aliyun.sts.service.AliyunStsService;
import com.minlia.cloud.body.Response;
import com.minlia.cloud.constant.ApiPrefix;
import com.minlia.module.audit.annotation.AuditLog;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 临时权限 前端控制器
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Api(tags = "Aliyun Sts")
@RestController
@RequestMapping(value = ApiPrefix.V1 + "aliyun/sts")
@RequiredArgsConstructor
public class AliyunStsController {

    private final AliyunStsService aliyunStsService;

    @AuditLog(type = AuditOperationTypeEnum.SELECT)
    @PreAuthorize(value = "isAuthenticated()")
    @ApiOperation(value = "获取凭证")
    @GetMapping
    public Response getCredentials() {
        return Response.success(aliyunStsService.getCredentials());
    }

}