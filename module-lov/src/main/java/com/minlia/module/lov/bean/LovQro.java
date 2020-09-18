package com.minlia.module.lov.bean;

import com.minlia.module.data.bean.QueryRequest;
import com.minlia.module.data.entity.BaseQueryEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

/**
 * @author garen
 * @version 1.0
 * @description
 * @date 2019/5/20 4:09 PM
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LovQro extends BaseQueryEntity {

    @ApiModelProperty(value = "所属租户")
    private Integer tenantId;

    @ApiModelProperty(value = "编码")
    @Size(max = 50)
    private String code;

    @ApiModelProperty(value = "名称")
    @Size(max = 100)
    private String name;

    @ApiModelProperty(value = "排序（升序）")
    private Integer sort;

    @ApiModelProperty(value = "描述信息")
    @Size(max = 200)
    private String description;

    @ApiModelProperty(value = "禁用标记")
    private Boolean disFlag;

}
