package com.minlia.module.approved.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedVO  extends AbstractEntity {

    /**
     * Request date & time
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime requestDateTime;

    /**
     * Requestor user name
     */
    private String requestorUserGuid;
    private String requestorUserName;

    /**
     * Function
     */
    private ApprovedFunctionEnum function;
    private String functionVal;

    /**
     * Identifier
     */
    private String identifier;

    /**
     * Status
     */
    private ApprovedStatusEnum status;

    /**
     * Approved / Rejected date & Time
     */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime approvedDateTime;

    /**
     * Approver user name
     */
    private String approverUserGuid;
    private String approverUserName;

    private String beforeData;
    private String afterData;
    /**
     * 1:显示all,2:显示before,3:显示after,4:显示详情界面,5:没有详情
     */
    private Integer detailFlag;
    /**
     * 需要显示的修改前后数据中的对比字段
     */
    private String detailDataField;
    /**
     * 审批需调用的接口方法url
     */
    private String approvalMethodUrl;

    /**
     * 备注
     */
    private String remark;
}