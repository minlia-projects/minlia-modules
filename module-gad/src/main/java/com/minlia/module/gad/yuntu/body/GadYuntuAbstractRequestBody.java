package com.minlia.module.gad.yuntu.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

/**
 * 创建、修改请求体
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuAbstractRequestBody implements ApiRequestBody {

    /**
     * 客户唯一标识   用户申请，由高德地图API后台自动分配     必填
     */
    @JsonIgnore
    private String key;

    /**
     * 数据表唯一标识  必填
     */
    @JsonIgnore
    private String tableid;

    /**
     * 数字签名     必填
     */
    @JsonIgnore
    private String sig;

}
