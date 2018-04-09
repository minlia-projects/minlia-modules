package com.minlia.modules.attachment.body;


import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@ApiModel(value = "附件-更新")
@Data
public class AttachmentUpdateRequestBody implements ApiRequestBody {

    @NotNull
    private Long id;

    /**
     * 附件名称
     */
    @NotNull
    private String name;

    /**
     * 附件类型
     */
    private String type;

    @ApiModelProperty(value = "附件类型",example = "jpg")
    @NotNull
    private String url;

    @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
    @NotNull
    private String accessKey;

}
