package com.minlia.module.lbsyun.body.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/**
 * 矩形检索请求体
 * Created by garen on 2018/6/9.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GeoCoderRequest extends GeoRequest{

    /**
     * 根据经纬度坐标获取地址。
     */
    @NotBlank
    private String location;

    /**
     * 输出格式为json或者xml
     * default:xml
     */
    private String output;

    /**
     * 坐标的类型，目前支持的坐标类型包括：bd09ll（百度经纬度坐标）、bd09mc（百度米制坐标）、gcj02ll（国测局经纬度坐标，仅限中国）、wgs84ll（ GPS经纬度
     * default:bd09ll
     */
    private String coordtype;

    /**
     * 可选参数，添加后返回国测局经纬度坐标或百度米制坐标
     * default:bd09ll（百度经纬度坐标）
     */
    private String ret_coordtype;

    /**
     * 是否召回传入坐标周边的poi，0为不召回，1为召回。当值为1时，默认显示周边1000米内的poi。
     * 注意：若需访问海外POI，需申请「逆地理编码海外POI」服务权限，请提交工单申请。
     * default:0
     */
    private Integer pois;

    /**
     * poi召回半径，允许设置区间为0-1000米，超过1000米按1000米召回。
     * default:1000
     */
    private Integer radius;

    /**
     * 将json格式的返回值通过callback函数返回以实现jsonp功能
     */
    private String callback;

    /**
     * 区别于pois参数，pois=0，不召回pois数据，但后端仍访问poi相应服务；extensions_poi=null时，后端不调用poi相关服务，可减少服务访问时延。
     * 注意：若需访问海外POI，需申请「逆地理编码海外POI」服务权限，请提交工单申请。
     */
    private String extensions_poi;

    /**
     * 当取值为true时，召回坐标周围最近的3条道路数据。区别于行政区划中的street参数（street参数为行政区划中的街道，和普通道路不对应）。
     * default:false
     */
    private String extensions_roadl;

    /**
     * 当取值为true时，行政区划返回乡镇级数据（仅国内召回乡镇数据）。默认不访问。
     */
    private String extensions_town;

    /**
     * 指定召回的新政区划语言类型。
     * 召回行政区划语言list（全量支持的语言见下方附录）。
     * 当language=local时，根据请求中坐标所对应国家的母语类型，自动选择对应语言类型的行政区划召回。
     * 目前支持多语言的行政区划区划包含country、provence、city、district
     * default:en，国内默认zh-CN
     */
    private String language;

    /**
     * 是否自动填充行政区划。
     * 1填充，0不填充。
     * 填充：当服务按某种语言类别召回时，若某一行政区划层级数据未覆盖，则按照“英文→中文→本地语言”类别行政区划数据对该层级行政区划进行填充，保证行政区划数据召回完整性。
     */
    private Integer language_auto;

    /**
     * 是否访问最新版行政区划数据（仅对中国数据生效），1（访问），0（不访问）
     * default:0
     */
    private Integer latest_admin;

}
