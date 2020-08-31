package com.minlia.module.approved.bean.ro;

import com.minlia.module.approved.enumeration.ApprovedFunctionEnum;
import com.minlia.module.approved.enumeration.ApprovedStatusEnum;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApprovedQRO extends QueryRequest {

    private Long id;

    private String createBy;

    private String lastModifiedBy;

    private LocalDateTime createDate;

    private LocalDateTime lastModifiedDate;

    /**
     * Request date & time
     */
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