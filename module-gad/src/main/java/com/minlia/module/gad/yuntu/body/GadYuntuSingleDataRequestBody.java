package com.minlia.module.gad.yuntu.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

/**
 * 创建、修改请求体
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuSingleDataRequestBody implements ApiRequestBody {

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
     * 定位方式     默认1
     * 1：经纬度；格式示例：104.394729,31.125698
     * 2：地址；标准格式示例：北京市朝阳区望京阜通东大街6号院3号楼
     */
    private Integer loctype;

    /**
     * 新增的数据    规则：json     必填
     */
    private String data;

}
