package com.minlia.modules.attachment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AuditableEntity;
import com.minlia.modules.attachment.enumeration.StorageTypeEnum;
import lombok.*;

/**
 * Created by will on 6/21/17.
 * 附件实体
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Attachment extends AuditableEntity {

    /**
     * 业务类型
     */
    private String belongsTo;

    /**
     * 业务ID
     */
    private String relationId;

    /**
     * 附件名称
     */
    private String name;

    /**
     * 附件类型
     */
    private String type;

    /**
     * 附件地址
     */
    private String url;

    /**
     * 附件大小
     */
    private Long size;

    /**
     * 访问令牌：234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d
     */
    private String accessKey;

    /**
     * 存储类型
     */
    private StorageTypeEnum storageType;

}
