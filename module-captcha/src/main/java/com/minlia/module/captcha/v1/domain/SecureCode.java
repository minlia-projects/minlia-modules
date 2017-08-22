package com.minlia.module.captcha.v1.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.entity.AbstractEntity;
import com.minlia.module.captcha.v1.enumeration.SecureCodeSceneEnum;
import lombok.Data;

import javax.persistence.*;

/**
 * System SecureCode class
 * 系统短信验证码实体
 */
@Data
@Entity
@Table(name = "secure_code")
public class SecureCode extends AbstractEntity {
    // ==============
    // PRIVATE FIELDS
    // ==============

    // The code status is used
    /**
     * 是否已被使用
     */
    @Column(name = "is_used", nullable = false)
    @JsonProperty
    private Boolean used = false;

    /**
     * 验证码内容
     */
    @JsonProperty
    private String code;

    /**
     * 消费此验证码的人
     */
    @JsonProperty
    private String consumer;

    /**
     * 验证码使用的场景
     */
    @Enumerated(EnumType.STRING)
    private SecureCodeSceneEnum scene;


}