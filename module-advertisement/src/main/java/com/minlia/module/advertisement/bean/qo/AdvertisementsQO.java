package com.minlia.module.advertisement.bean.qo;

import com.minlia.module.advertisement.enumeration.PlatformEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdvertisementsQO {

    private Long id;

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
     * 是否启用
     */
    private Boolean enabled;

}