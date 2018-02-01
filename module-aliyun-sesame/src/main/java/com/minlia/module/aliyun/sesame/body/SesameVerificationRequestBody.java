package com.minlia.module.aliyun.sesame.body;

import com.minlia.cloud.body.ApiRequestBody;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class SesameVerificationRequestBody implements ApiRequestBody {

    @ApiModelProperty(value = "姓名")
    @NotBlank(message = "必填")
    private String name;

    @ApiModelProperty(value = "身份证")
    @NotBlank(message = "必填")
//    @IdCard
    private String certNo;

}
