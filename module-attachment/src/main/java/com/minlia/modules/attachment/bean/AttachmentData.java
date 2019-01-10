package com.minlia.modules.attachment.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/12/30.
 */
@Data
public class AttachmentData {

    @ApiModelProperty(value = "附件类型",example = "jpg")
    @NotNull
    private String url;

    @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
    @NotNull
    private String accessKey;

}