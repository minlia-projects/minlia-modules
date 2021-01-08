package com.minlia.module.library.bean;

import com.minlia.module.data.entity.AbstractEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
public class SysLibrarySummaryVo extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "关键字")
    private String url;

}