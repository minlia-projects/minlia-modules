package com.minlia.module.audit.bean;

import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author garen
 */
@Data
@ApiModel
public class AuditLogQro extends BaseQueryEntity {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "操作方式")
    private String method;

    @ApiModelProperty(value = "操作类型")
    private AuditOperationTypeEnum operationType;

    @ApiModelProperty(value = "是否成功")
    private Boolean successFlag;

    @ApiModelProperty(value = "事件标签")
    private String tags;

    @ApiModelProperty(value = "日志标题")
    private String title;

    @ApiModelProperty(value = "服务ID")
    private String serviceId;

    @ApiModelProperty(value = "操作IP地址")
    private String remoteAddr;

    @ApiModelProperty(value = "用户代理")
    private String userAgent;

    @ApiModelProperty(value = "请求URI")
    private String requestUri;

    @ApiModelProperty(value = "操作提交的数据")
    private String params;

    @ApiModelProperty(value = "执行时间")
    private Long time;

    @ApiModelProperty(value = "删除标记")
    private Boolean delFlag;

}