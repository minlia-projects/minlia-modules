package com.minlia.module.approved.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.common.constant.LocalDateConstants;
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
public class Approved extends AbstractEntity {
    private Long id;

    /**
    * Request date & time
    */
    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)
    private LocalDateTime requestDateTime;

    /**
    * Requestor user name
    */
    private String requestorUserGuid;

    /**
    * Function
    */
    private ApprovedFunctionEnum function;

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

    private String beforeData;

    private String afterData;

    /**
     * 备注
     */
    private String remark;
}