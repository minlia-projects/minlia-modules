package com.minlia.modules.attachment.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.module.data.entity.AbstractEntity;
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
public class Attachment extends AbstractEntity {

    /**
     * 业务类型
     */
    @JsonProperty
    private String businessType;

    /**
     * 业务ID
     */
    @JsonProperty
    private String businessId;

    /**
     * 附件名称
     */
    @JsonProperty
    private String name;

    /**
     * 附件类型
     */
    @JsonProperty
    private String type;

    /**
     * 附件地址
     */
    @JsonProperty
    private String url;

    /**
     * 附件大小
     */
    @JsonProperty
    private Long size;

    /**
     * 访问令牌：234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d
     */
    private String accessKey;

}
