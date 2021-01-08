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
public class SysLibraryVo extends AbstractEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "业务类型")
    private String type;

    @ApiModelProperty(value = "关键字")
    private String keyword;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "连接")
    private String url;

    @ApiModelProperty(value = "概要")
    private String summary;

}