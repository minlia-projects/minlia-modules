package com.minlia.module.library.bean;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>
 * 文库
 * </p>
 *
 * @author garen
 * @since 2020-09-11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SysLibraryOcrVo {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "连接")
    private String url;

    @ApiModelProperty(value = "内容")
    private String content;

}