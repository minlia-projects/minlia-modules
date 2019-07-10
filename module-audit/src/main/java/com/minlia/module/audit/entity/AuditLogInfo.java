package com.minlia.module.audit.entity;

import com.minlia.module.audit.enumeration.OperationTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.Data;

@Data
public class AuditLogInfo extends AbstractEntity {

    /**
     * 日志类型
     */
    private OperationTypeEnum operationType;

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
     * 操作方式
     */
    private String method;

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
    private Integer delFlag = 0;

    /**
     * 异常信息
     */
    private String exception;

}