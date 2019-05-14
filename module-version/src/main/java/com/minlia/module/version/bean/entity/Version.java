package com.minlia.module.version.bean.entity;

import com.minlia.module.data.entity.AbstractEntity;
import com.minlia.module.version.enumeration.PlatformTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 版本
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Version extends AbstractEntity {

    /**
     * 版本号
     */
    private String number;

    /**
     * 平台类型
     */
    private PlatformTypeEnum type;

    /**
     * 下载链接
     */
    private String downloadUrl;

    /**
     * 备注
     */
    private String notes;

    /**
     * 是否测试
     */
    private Boolean test;

    /**
     * 是否启用
     */
    private Boolean enabled;

    /**
     * 强制下载
     */
    private Boolean forcedDownload;

    private String attribute1;

    private String attribute2;

    private String attribute3;

    private String attribute4;

    private String attribute5;

}
