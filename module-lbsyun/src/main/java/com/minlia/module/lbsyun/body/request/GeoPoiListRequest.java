package com.minlia.module.lbsyun.body.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 查询指定id的数据（poi） 请求体
 * Created by garen on 2018/6/9.
 */
@Data
public class GeoPoiListRequest extends GeoRequest{

    /**
     * 用户在column定义的key/value对
     * 可选。column需要设置了is_index_field=1。对于string，是前缀匹配，如需精确匹配请在字段值末尾加$。对于int或者double，则是范围查找，传递的格式为最小值,最大值。当无最小值或者最大值时，用-代替，同时，此字段最大长度不超过50，最小值与最大值都是整数
     * 例：如加入一个命名为color数据类型为string的column，在检索是可设置为“color=red”的形式来检索color字段为red的POI。
     * 如加入一个命名为number数据类型为int的column，在检索是可设置为“number=20,-”的形式来检索number字段值大于等于20的POI。
     */
    private String keys;

    /**
     * 位置数据名称
     */
    @Size(max = 128)
    private String title;

    /**
     * geotable持有数据的类型
     * 空格分隔的多字符串
     */
    @Size(max = 256)
    private String tags;

    /**
     * 查询的矩形区域
     * 可选。格式x1,y1;x2,y2分别代表矩形的左上角和右下角。
     * 范围过大时服务易超时，返回数据为空。
     * 建议经度跨度小于0.8，纬度跨度小于0.5。
     */
    @Size(max = 100)
    private String bounds;

    /**
     * 用户上传的坐标的类型：1、2、3、4
     * 1：GPS经纬度坐标
     * 2：国测局加密经纬度坐标
     * 3：百度加密经纬度坐标
     * 4：百度加密墨卡托坐标
     *
     */
    @NotNull
    private Integer coord_type;

    /**
     * 分页索引
     * 默认为0
     */
    private Integer page_index;

    /**
     * 当前页面最大结果数:默认为10，最多为50
     */
    private Integer page_size;

}
