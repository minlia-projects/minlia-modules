package com.minlia.module.bible.v1.body;

import com.minlia.cloud.body.query.QueryOperator;
import com.minlia.cloud.query.body.QueryRequestBody;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by will on 7/5/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "数据字典搜索请求体")
public class BibleQueryRequestBody implements QueryRequestBody {

    @ApiModelProperty(value = "编码")
    private String code;
    @ApiModelProperty(value = "编码搜索符")
    private QueryOperator codeOperator;


    @ApiModelProperty(value = "名称,标签")
    private String label;
    @ApiModelProperty(value = "名称,标签搜索符")
    private QueryOperator labelOperator;

    @ApiModelProperty(value = "备注")
    private String notes;
    @ApiModelProperty(value = "备注搜索符")
    private QueryOperator notesOperator;


}
