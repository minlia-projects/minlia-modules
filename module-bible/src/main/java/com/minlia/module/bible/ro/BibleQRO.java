package com.minlia.module.bible.ro;

import com.minlia.module.data.bean.QueryRequest;
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
public class BibleQRO extends QueryRequest {

  @ApiModelProperty(value = "编码")
  private String code;

//  @ApiModelProperty(value = "编码搜索符")
//  private QueryOperator codeOperator;

//  @ApiModelProperty(value = "名称,标签搜索符")
//  private QueryOperator labelOperator;
//
//  @ApiModelProperty(value = "备注")
//  private String notes;
//
//  @ApiModelProperty(value = "备注搜索符")
//  private QueryOperator notesOperator;

}
