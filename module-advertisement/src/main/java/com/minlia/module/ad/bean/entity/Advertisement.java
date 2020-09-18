package com.minlia.module.ad.bean.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.minlia.module.ad.enumeration.AdTypeEnum;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 广告
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Advertisement extends AbstractEntity {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 封面
     */
//    private String cover;
    @JsonIgnoreProperties(value = {"belongsTo", "relationId", "storageType", "createBy", "createDate", "lastModifiedBy", "lastModifiedDate"})
    private Attachment cover;


    /**
     * 链接类型（page，url）
     */
    private AdTypeEnum type;

    /**
     * 链接地址
     */
    private String path;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private Boolean enabled;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

    private String attribute5;

}
