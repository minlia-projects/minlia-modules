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
import java.util.List;

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
public class LovValueQRO extends QueryRequest {

    /**
     * 值集ID
     */
    private Long lovId;

    private String lovCode;


    private String parentId;

    /**
     * 编码
     */
    @Size(max = 100)
    private String code;

    private List<String> codes;

    /**
     * 名称
     */
    @Size(max = 100)
    private String name;

//    /**
//     * 排序（升序）
//     */
//    private Byte sort;

    /**
     * 描述信息
     */
    @Size(max = 255)
    private String description;

    /**
     * 语言环境
     */
    private String locale;

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

    private String createBy;

    private String lastModifiedBy;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime createDate;

    @JsonFormat(pattern = LocalDateConstants.DEFAULT_LOCAL_DATE_TIME_FORMAT)     private LocalDateTime lastModifiedDate;

}
