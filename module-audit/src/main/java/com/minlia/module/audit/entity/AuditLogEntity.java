package com.minlia.module.audit.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.minlia.module.audit.enums.AuditOperationTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * <p>
 * 审计日志
 * </p>
 *
 * @author garen
 * @since 2020-08-21
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_audit_log")
@ApiModel(value = "AuditLogEntity对象", description = "审计日志")
public class AuditLogEntity extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户名")
    @TableField("username")
    private String username;

    @ApiModelProperty(value = "操作方式")
    @TableField("method")
    private String method;

    @ApiModelProperty(value = "操作类型")
    @TableField("operation_type")
    private AuditOperationTypeEnum operationType;

    @ApiModelProperty(value = "是否成功")
    @TableField("success_flag")
    private Boolean successFlag;

    @ApiModelProperty(value = "事件标签")
    @TableField("tags")
    private String tags;

    @ApiModelProperty(value = "日志标题")
    @TableField("title")
    private String title;

    @ApiModelProperty(value = "服务ID")
    @TableField("service_id")
    private String serviceId;

    @ApiModelProperty(value = "操作IP地址")
    @TableField("remote_addr")
    private String remoteAddr;

    @ApiModelProperty(value = "用户代理")
    @TableField("user_agent")
    private String userAgent;

    @ApiModelProperty(value = "请求URI")
    @TableField("request_uri")
    private String requestUri;

    @ApiModelProperty(value = "操作提交的数据")
    @TableField("params")
    private String params;

    @ApiModelProperty(value = "执行时间")
    @TableField("time")
    private Long time;

    @ApiModelProperty(value = "异常信息")
    @TableField("exception")
    private String exception;

    @ApiModelProperty(value = "删除标记")
    @TableField("del_flag")
    @TableLogic
    private Boolean delFlag;

}
