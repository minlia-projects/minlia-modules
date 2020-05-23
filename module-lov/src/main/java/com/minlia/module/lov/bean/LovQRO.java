package com.minlia.module.lov.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.minlia.module.common.constant.LocalDateConstants;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/20 4:09 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LovQRO extends QueryRequest {

    /**
     * 编码
     */
    @Size(max = 100)
    private String code;

    /**
     * 名称
     */
    @Size(max = 100)
    private String name;

    /**
     * 描述信息
     */
    @Size(max = 255)
    private String description;

    /**
     * 禁用标记
     */
    private Boolean disFlag;

    /**
     * 删除标记
     */
    private Boolean delFlag;

    /**
     * 所属租户
     */
    private Integer tenantId;


    private Long id;

    @Size(max = 15)
    private String createBy;

    @Size(max = 15)
    private String lastModifiedBy;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime createDate;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime lastModifiedDate;

}
