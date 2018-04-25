package com.minlia.module.map.yuntu.body;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 创建、修改请求体
 * Created by garen on 2017/12/27.
 */
@Data
@AllArgsConstructor
public class GadYuntuSearchIDRequestBody extends GadYuntuAbstractRequestBody {

    /**
     * 数据id     必填
     */
    @NotNull
    private Long _id;

}
