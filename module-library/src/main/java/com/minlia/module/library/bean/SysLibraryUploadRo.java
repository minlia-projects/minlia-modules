package com.minlia.module.library.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
@ApiModel(value = "SysLibraryUploadRo", description = "文库")
public class SysLibraryUploadRo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    @NotBlank
    @Size(max = 20)
    private String type;

    @ApiModelProperty(value = "关键字")
    @NotBlank
    @Size(max = 50)
    private String keyword;

    @ApiModelProperty(value = "备注")
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}