package com.minlia.module.library.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@ApiModel(value = "SysLibrarySro", description = "文库")
public class SysLibrarySro implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "业务类型")
    @NotBlank
    @Size(max = 20)
    private String type;

    @ApiModelProperty(value = "关键字")
    @NotBlank
    @Size(max = 50)
    private String keyword;

    @ApiModelProperty(value = "令牌")
    @NotBlank
    @Size(max = 32)
    private String accessKey;

    @ApiModelProperty(value = "文件名称")
    @NotBlank
    @Size(max = 50)
    private String fileName;

    @ApiModelProperty(value = "文件类型")
    @NotBlank
    @Size(max = 50)
    private String fileType;

    @ApiModelProperty(value = "大小")
    @NotNull
    private Long fileSize;

    @ApiModelProperty(value = "地址")
    @NotBlank
    @Size(max = 255)
    private String url;

    @ApiModelProperty(value = "备注")
    @NotBlank
    @Size(max = 255)
    private String remark;

    @ApiModelProperty(value = "是否禁用")
    private Boolean disFlag;

}