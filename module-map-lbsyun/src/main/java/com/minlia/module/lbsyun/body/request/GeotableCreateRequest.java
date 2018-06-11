package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

/**
 * 创建表（create geotable）接口 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeotableCreateRequest implements ApiRequestBody{

    /**
     * geotable的中文名称
     * 必选、string(45)
     */
    private String name;

    /**
     * geotable持有数据的类型
     * 必选
     * 1：点；3：面。默认为1（如需存储“面”数据，建议使用V4版云存储服务）
     */
    private Integer geotype;

    /**
     * 是否发布到检索
     * 必选
     * 0：未自动发布到云检索，
     * 1：自动发布到云检索；
     * 注：
     * 1）通过URL方式创建表时只有is_published=1时 在云检索时表内的数据才能被检索到。
     * 2）可通过数据管理模块设置，在设置中将是否发送到检索一栏中选定为是即可。
     */
    private Integer is_published;

    /**
     * 用户的访问权限key
     * 必选、string(50)
     */
    private String ak;

    /**
     * 用户的权限签名
     * string(50)
     * 可选,若用户所用AK的校验方式为SN校验时该参数必须（SN生成算法）
     * 若AK设置为SN校验，所有云存储接口均需拼写SN参数，为节省篇幅，后续接口不再单独说明。
     */
    private String sn;

    /**
     * 时间戳：配合sn使用，增加时间戳安全验证
     * 可选
     */
    private Long timestamp;

}
