package com.minlia.module.pooul.bean.qo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotBlank;

/**
 * Created by garen on 2018/9/25.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulCmbaYqQO {

    @ApiModelProperty(value = "平台商户编号", example = "2162288807443437")
    @NotBlank
    private String merchant_id;

    @ApiModelProperty(value = "起始日期", example = "20180723")
    @NotBlank
    private String date_start;

    @ApiModelProperty(value = "结束日期", example = "20181212")
    @NotBlank
    private String date_end;

    @ApiModelProperty(value = "开始条数", example = "0")
    @NotBlank
    private int start_no;

    @ApiModelProperty(value = "结束条数", example = "20")
    @NotBlank
    private int end_no;

}
