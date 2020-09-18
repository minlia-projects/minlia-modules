package com.minlia.module.data.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Getter
@Setter
public abstract class WithIdEntity implements Serializable {

    /**
     * 主键
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonSerialize(using = ToStringSerializer.class)
    @TableId(value = "id")
    private Long id;

}