package com.minlia.module.lbsyun.body.request;

import lombok.Data;

/**
 * 创建表（create geotable）接口 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoSearchRequest extends GeoRequest{

    /**
     * 检索关键字	:任意汉字或数字，英文字母，可以为空字符
     * 必填：string(45)
     */
    private String q;

    /**
     * 3代表百度经纬度坐标系统 4代表百度墨卡托系统
     */
    private Integer coord_type;

    /**
     * geotable持有数据的类型
     * 空格分隔的多字符串
     */
    private String tags;

    /**
     * 排序字段
     * ”分隔的多个检索条件。
     格式为sortby={key1}:value1|{key2:val2|key3:val3}。 最多支持16个字段排序 {keyname}:1 升序 {keyname}:-1 降序 以下keyname为系统预定义的： distance 距离排序 weight 权重排序
     * 可选 默认为按weight排序 如果需要自定义排序则指定排序字段 样例：按照价格由便宜到贵排序 sortby=price:1
     */
    private String sortby;

    /**
     * 过滤条件
     * string(50)
     * 竖线分隔的多个key-value对
     key为筛选字段的名称(存储服务中定义) 支持连续区间或者离散区间的筛选： a:连续区间 key:value1,value2 b:离散区间 key:[value1,value2,value3,...]
     */
    private String filter;

    /**
     * 分页索引
     * 默认为0
     */
    private Integer page_index;

    /**
     * 当前页面最大结果数:默认为10，最多为50
     */
    private Integer page_size;

    /**
     * 用户的权限签名
     * string(50)
     * 可选,若用户所用AK的校验方式为SN校验时该参数必须（SN生成算法）
     * 若AK设置为SN校验，所有云存储接口均需拼写SN参数，为节省篇幅，后续接口不再单独说明。
     */
    private String sn;

}
