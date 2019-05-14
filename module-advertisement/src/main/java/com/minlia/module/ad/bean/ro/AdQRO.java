package com.minlia.module.ad.bean.ro;

import com.minlia.module.ad.enumeration.AdTypeEnum;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdQRO extends QueryRequest {

    /**
     * 父ID
     */
    private Long parentId;

    /**
     * 名称
     */
    private String name;

    /**
     * 链接类型（page，url）
     */
    private AdTypeEnum type;

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