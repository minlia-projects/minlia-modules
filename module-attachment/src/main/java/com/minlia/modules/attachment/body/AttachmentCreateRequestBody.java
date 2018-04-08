package com.minlia.modules.attachment.body;


import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ApiModel(value = "附件-创建")
@Data
public class AttachmentCreateRequestBody implements ApiRequestBody {

    @ApiModelProperty(value = "业务类型编码", example = "account.identity.frontend")
    @NotNull
    private String belongsTo;

    @ApiModelProperty(value = "业务ID", example = "123")
    @NotNull
    private String relationId;

    @ApiModelProperty(value = "数据")
    @NotNull
    private List<AttachmentData> data;

}
