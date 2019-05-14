package com.minlia.module.data.bean;

import com.google.common.base.CaseFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created by will on 3/19/17.
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode(of = {"filed"})
public class Sort {

    @ApiModelProperty(example = "id")
    private String filed;

    @ApiModelProperty(example = "DESC")
    private Direction direction;

    public String getFiled() {
        return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.filed);
    }

    public enum Direction {
        ASC,
        DESC
    }

}