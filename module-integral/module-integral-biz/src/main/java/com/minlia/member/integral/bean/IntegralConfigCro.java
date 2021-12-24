package com.minlia.member.integral.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * <p>
 * 积分-配置
 * </p>
 *
 * @author garen
 * @since 2021-04-14
 */
@Data
@ApiModel(value = "IntegralConfigCro")
public class IntegralConfigCro {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务类型")
    @NotBlank
    @Size(max = 100)
    private String businessType;

    @ApiModelProperty(value = "名称")
    @NotBlank
    @Size(max = 100)
    private String name;

    @ApiModelProperty(value = "介绍")
    @NotBlank
    @Size(max = 500)
    private String introduce;

    @ApiModelProperty(value = "分数")
    @NotNull
    @Min(1)
    private Long quantity;

    @ApiModelProperty(value = "开始时间")
    @NotNull
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @NotNull
    private LocalDateTime endTime;

    @ApiModelProperty(value = "是否启用")
    private Boolean disFlag;

}