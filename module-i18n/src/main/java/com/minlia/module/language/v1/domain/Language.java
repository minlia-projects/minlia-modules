package com.minlia.module.language.v1.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.minlia.cloud.data.support.constant.PersistenceConstants;
import com.minlia.cloud.entity.AbstractEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * 国际化语言实体
 * @author: Minlia Speedup Code Engine
 * @since: 1.0.0.RELEASE
 * Navigation Define as a JPA entity
 */
// Is those properties publish to json
@JsonIgnoreProperties(value = {})
// A JPA Annotation to define as a entity
@Entity
// Json sort order
@JsonPropertyOrder({})
// A JPA annotation to define as data table name, it will convert to camel case (eg. hello_word) when multiple words
@Table(name = PersistenceConstants.MODULE_TABLE_PREFIX + "Language"
// ,
// uniqueConstraints={@UniqueConstraint(columnNames={ })}
)
// A JPA annotation to define how to generate sequence
@SequenceGenerator(name = PersistenceConstants.SEQUENCE_GENERATOR_NAME, sequenceName = PersistenceConstants.SEQUENCE_PREFIX + "Language")
// A Lombok annotation to generate Getter and Setter on compilation
// A Lombok annotation to generate ${className}.Builder inner class as a helper class on compilation
@Builder
// A Lombok annotation to generate toString method on compilation
@ToString(exclude = {"children"})
// A Lombok annotation to generate equals hashCode methods on compilation
@EqualsAndHashCode(exclude = {"children"})
// A Lombok annotation to generate all arguments included constructor on compilation
@AllArgsConstructor
@NoArgsConstructor
// A Minlia Speedup annotation to generation code on compilation
public class Language extends AbstractEntity {

    /**
     * 模块唯一标识
     * 模块ID: security
     *
     */
    @JsonProperty
    private String basename;

    /**
     * 与country相加等于
     * ISO国家编码
     * zh_CN
     * en_US
     */
    @JsonProperty
    private String language;
    @JsonProperty
    private String country;
    @JsonProperty
    private String variant;

    /**
     * 国际化键 如:
     * 错误编码
     * basename+ exceptions.api.code.1100
     * 作为对象属性时
     * basename+ com.sun.domain.User.username zh_CN content
     */
    @JsonProperty
    private String code;

    /**
     * 国际化值
     */
    @JsonProperty
    private String message;


    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getVariant() {
        return variant;
    }

    public void setVariant(String variant) {
        this.variant = variant;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
