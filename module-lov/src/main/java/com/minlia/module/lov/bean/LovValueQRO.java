package com.minlia.module.lov.bean;

import com.minlia.module.data.bean.QueryRequest;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.Date;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/20 4:09 PM
 */
@Data
public class LovValueQRO extends QueryRequest {

    /**
     * 值集ID
     */
    private Long lovId;

    private String lovCode;

    private Long parentId;

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

    private Date createDate;

    private Date lastModifiedDate;

}
