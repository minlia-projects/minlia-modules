package com.minlia.module.attachment.bean;


import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * @author garen
 */
@ApiModel(value = "附件-查询")
@Data
public class AttachmentQro extends BaseQueryEntity {

    @ApiModelProperty(value = "业务类型", example = "account.identity.frontend")
    @Size(max = 20)
    private String relationTo;

    @ApiModelProperty(value = "业务ID")
    @Size(max = 32)
    private String relationId;

    @ApiModelProperty(value = "访问令牌", example = "c32175b9375f4157992507affff95e05")
    @Size(max = 32)
    private String accessKey;

    @ApiModelProperty(value = "附件类型", example = "jpg")
    @Size(max = 20)
    private String type;

    @ApiModelProperty(value = "名称")
    @Size(max = 20)
    private String name;

    @ApiModelProperty(value = "地址")
    @Size(max = 300)
    private String url;

}