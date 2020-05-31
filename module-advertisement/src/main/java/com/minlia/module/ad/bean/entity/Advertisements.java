package com.minlia.module.ad.bean.entity;

import com.minlia.module.ad.enumeration.PlatformEnum;
import com.minlia.module.data.entity.AbstractEntity;
import lombok.*;

import java.util.List;

/**
 * 广告集
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(of = "id")
public class Advertisements extends AbstractEntity {

    /**
     * 名称
     */
    private String name;

    /**
     * 平台
     */
    private PlatformEnum platform;

    /**
     * 投放位置
     */
    private String position;

    /**
     * 横纵比
     */
    private String aspectRatio;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否启用
     */
    private Boolean enabled;

    List<Advertisement> advertisements;

}
