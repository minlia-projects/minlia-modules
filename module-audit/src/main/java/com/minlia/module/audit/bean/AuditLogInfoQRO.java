package com.minlia.module.audit.bean;

import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.data.bean.QueryRequest;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel
public class AuditLogInfoQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDate createDate;

    private LocalDate lastModifiedDate;

    private LocalDateTime createDateTime;

    private LocalDateTime lastModifiedDateTime;

    private LocalDateTime ltCreateDateTime;

    private LocalDateTime gtLastModifiedDateTime;

    /**
     * 用户名
     */
    private String username;

    /**
     * 操作方式
     */
    private String method;

    /**
     * 操作类型
     */
    private OperationTypeEnum operationType;

    /**
     * 事件标签
     */
    private String tags;

    /**
     * 日志标题
     */
    private String title;

    /**
     * 服务ID
     */
    private String serviceId;

    /**
     * 操作IP地址
     */
    private String remoteAddr;

    /**
     * 用户代理
     */
    private String userAgent;

    /**
     * 请求URI
     */
    private String requestUri;

    /**
     * 操作提交的数据
     */
    private String params;

    /**
     * 执行时间
     */
    private Long time;

    /**
     * 删除标记
     */
    private Integer delFlag;

    /**
     * 异常信息
     */
    private String exception;

}
