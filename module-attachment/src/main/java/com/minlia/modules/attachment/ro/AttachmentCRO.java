package com.minlia.modules.attachment.ro;


import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "附件-创建")
@lombok.Data

public class AttachmentCRO implements ApiRequestBody {

    @ApiModelProperty(value = "业务类型编码", example = "account.identity.frontend")
    @NotNull
    private String belongsTo;

    @ApiModelProperty(value = "业务ID", example = "123")
    @NotNull
    private String relationId;

    @ApiModelProperty(value = "数据")
    @NotNull
    private List<Data> data;

    @lombok.Data
    public static class Data {

        @ApiModelProperty(value = "附件类型",example = "jpg")
        @NotNull
        private String url;

        @ApiModelProperty(value = "访问令牌", example = "234ehdskjds-sdfas-dfds-fds-fa-sdfs-sdfas-d")
        @NotNull
        private String accessKey;

    }

}
