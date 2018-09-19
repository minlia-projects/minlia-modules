package com.minlia.module.gad.yuntu.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2017/12/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GadYuntuDeleteRequestBody implements ApiRequestBody {

    /**
     * 客户唯一标识   用户申请，由高德地图API后台自动分配     必填
     */
    private String key;

    /**
     * 数据表唯一标识  必填
     */
    private String tableid;

    /**
     * 数字签名     必填
     */
    private String sig;

    /**
     * 一次请求限制1-50条数据，多个_id用逗号隔开
     */
    private String ids;

}
