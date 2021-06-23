package com.minlia.module.attachment.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * <p>
 * 附件
 * </p>
 *
 * @author garen
 * @since 2020-08-26
 */
@Data
@ApiModel(value = "AttachmentCro", description = "附件")
public class AttachmentCro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "令牌")
    @NotBlank
    @Size(max = 32)
    private String accessKey;

    @ApiModelProperty(value = "名称")
    @NotBlank
    private String name;

    @ApiModelProperty(value = "类型")
    @NotBlank
    private String type;

    @ApiModelProperty(value = "地址")
    @NotBlank
    @Size(max = 255)
    private String url;

    @ApiModelProperty(value = "大小")
    private Long size;

}