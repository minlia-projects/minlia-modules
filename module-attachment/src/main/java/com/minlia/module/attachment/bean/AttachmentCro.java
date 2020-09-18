package com.minlia.module.attachment.bean;


import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author garen
 */
@ApiModel(value = "附件-创建")
@lombok.Data
public class AttachmentCro implements ApiRequestBody {

    @ApiModelProperty(value = "业务类型", example = "account.identity.frontend")
    @NotBlank
    @Size(max = 20)
    private String relationTo;

    @ApiModelProperty(value = "业务ID", example = "123")
    @NotBlank
    @Size(max = 32)
    private String relationId;

    @ApiModelProperty(value = "数据")
    @NotEmpty
    private List<Data> data;

    @lombok.Data
    public static class Data {

        @ApiModelProperty(value = "附件类型", example = "jpg")
        @NotBlank
        @Size(max = 300)
        private String url;

        @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
        @NotBlank
        @Size(max = 32)
        private String accessKey;

    }

}