package com.minlia.module.aliyun.sesame.body;

import com.minlia.cloud.body.ApiRequestBody;
import com.minlia.module.common.validation.IdCard;
import com.minlia.module.common.validation.NameZh;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import javax.validation.constraints.NotBlank;

@Data
public class SesameVerificationRequest implements ApiRequestBody {

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "姓名不能为空")
    @NameZh
    private String name;

    @ApiModelProperty(value = "身份证")
    @NotBlank(message = "身份证不能为空")
    @IdCard
    private String certNo;

}
