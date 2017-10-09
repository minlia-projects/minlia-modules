package com.minlia.module.attachment.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.data.support.constant.PersistenceConstants;
import com.minlia.cloud.entity.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by will on 6/21/17.
 * 附件实体
 */

/**
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE
 * Attachment Define as a JPA entity
 */
// Is those properties publish to json
@JsonIgnoreProperties(value = {})
// A JPA Annotation to define as a entity
@Entity
// Json sort order
@JsonPropertyOrder({})
// A JPA annotation to define as data table name, it will convert to camel case (eg. hello_word) when multiple words
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Attachment"
// ,
// uniqueConstraints={@UniqueConstraint(columnNames={ })}
)
// A JPA annotation to define how to generate sequence
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "Attachment")
// A Lombok annotation to generate Getter and Setter on compilation
@Data
// A Lombok annotation to generate ${className}.Builder inner class as a helper class on compilation
@Builder
// A Lombok annotation to generate toString method on compilation
@ToString(of = {"id"})
// A Lombok annotation to generate equals hashCode methods on compilation
@EqualsAndHashCode(of = {"id"})
// A Lombok annotation to generate all arguments included constructor on compilation
@AllArgsConstructor
@NoArgsConstructor
// A Minlia Speedup annotation to generation code on compilation
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

    @ApiModelProperty(value = "附件类型",example = "jpg")
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
