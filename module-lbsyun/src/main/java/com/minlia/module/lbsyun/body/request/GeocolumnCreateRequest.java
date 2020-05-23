package com.minlia.module.lbsyun.body.request;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 创建表（create geotable）接口 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeocolumnCreateRequest implements ApiRequestBody{

    /**
     * geotable的中文名称
     * 必选、string(45)
     */
    @NotBlank
    private String name;

    /**
     * column存储的key标识，含义与返回结果中的列“id”字段相同，该字段为用户创建时自定义设置
     * 必选、string(45)、同一个geotable内的名字不能相同
     */
    @NotBlank
    private String key;

    /**
     * 存储的值的类型
     * 必选、uint32、枚举值1：Int64, 2：double, 3：string, 4：在线图片url
     */
    @NotNull
    private Integer type;

    /**
     * 最大长度
     * 可选、最大值2048，最小值为1。当type为string该字段有效，此时该字段必填。此值代表utf8的汉字个数，不是字节个数
     */
    private Integer max_length;

    /**
     * 默认值
     * string(45)
     */
    private String default_value;

    /**
     * 【云检索相关】是否检索引擎的数值排序筛选字段
     * 必选、1代表是，0代表否。设置后，在请求 LBS.云检索时可针对该字段进行排序。该字段只能为int或double类型，最多设置15个
     */
    @NotNull
    private Integer is_sortfilter_field;

    /**
     * 【云检索相关】是否检索引擎的文本检索字段
     * 必选、1代表支持，0为不支持。只有type为string时可以设置检索字段，只能用于字符串类型的列且最大长度不能超过512个字节
     */
    @NotNull
    private Integer is_search_field;

    /**
     * 【云存储相关】是否将字段设置为云存储的索引字段；
     * 必选
     * 用于存储接口查询:1代表支持，0为不支持
     * 注：is_index_field=1 时才能在根据该列属性值检索时检索到数据。
     */
    private Integer is_index_field;

    /**
     * 【云存储相关】是否云存储唯一索引字段，方便更新，删除，查询
     * 可选、1代表是，0代表否。设置后将在数据创建和更新时进行该字段唯一性检查，并可以以此字段为条件进行数据的更新、删除和查询。最多设置1个
     */
    private Integer is_unique_field;

    @NotBlank
    private String geotable_id;

    /**
     * 必选  申请ak
     */
    @NotBlank
    private String ak;

}
