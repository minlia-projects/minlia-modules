package com.minlia.module.advertisement.bean.qo;

import com.minlia.module.advertisement.enumeration.AdTypeEnum;
import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementQO extends QueryRequest {

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

}