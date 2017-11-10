package com.minlia.module.attachment.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.data.support.constant.PersistenceConstants;


import com.minlia.cloud.entity.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;


import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Created by will on 6/21/17.
 * 附件实体
 */
@Entity
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Attachment" /*, uniqueConstraints={@UniqueConstraint(columnNames={ })}*/)
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "Attachment")

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({})
@JsonIgnoreProperties(value = {})
@ToString(of = {"id"})
@EqualsAndHashCode(of = {"id"})
public class Attachment extends AbstractEntity {

    @ApiModelProperty(value = "业务类型编码", example = "account.identity.frontend")
    @JsonProperty
    private String businessType;

    @ApiModelProperty(value = "业务ID", example = "123")
    @JsonProperty
    private String businessId;

    @ApiModelProperty(value = "附件名称")
    @JsonProperty
    private String name;

    @ApiModelProperty(value = "附件类型", example = "jpg")
    @JsonProperty
    private String type;

    @ApiModelProperty(value = "附件地址", example = "https://oss.cdn.com/images/1.png")
    @JsonProperty
    private String url;

    @ApiModelProperty(value = "附件大小")
    @JsonProperty
    private Long size;

    @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
    private String accessToken;

}
