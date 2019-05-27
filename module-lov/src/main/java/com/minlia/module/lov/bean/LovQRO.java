package com.minlia.module.lov.bean;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.util.Date;

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

    private String createBy;

    private String lastModifiedBy;

    private Date createDate;

    private Date lastModifiedDate;

}
