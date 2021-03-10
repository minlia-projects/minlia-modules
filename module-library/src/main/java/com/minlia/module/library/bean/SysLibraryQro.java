package com.minlia.module.library.bean;

import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Size;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
@ApiModel(value = "SysLibraryQro", description = "文库")
public class SysLibraryQro extends BaseQueryEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @Size(max = 32)
    private String number;

    @ApiModelProperty(value = "业务类型")
    @Size(max = 20)
    private String type;

    @ApiModelProperty(value = "关键字")
    @Size(max = 50)
    private String keyword;

    @ApiModelProperty(value = "名称")
    @Size(max = 200)
    private String name;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}