package com.minlia.module.gad.place.body.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 周边搜索API服务地址：
 * Created by garen on 2017/12/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GadPlaceAbstractRequestBody implements Serializable {

    /**
     * 请求服务权限标识
     * 用户在高德地图官网
     * 必填
     */
    @JsonIgnore
    private String key;

    /**
     * 查询关键字
     * 规则： 多个关键字用“|”分割
     * 可选
     */
    private String keywords;

    /**
     * 查询POI类型
     * 多个类型用“|”分割
     * 当keywords和types均为空的时候，默认指定types为050000（餐饮服务）、070000（生活服务）、120000（商务住宅）
     * 可选
     */
    private String types;

    /**
     * 每页记录数据
     * 强烈建议不超过25，若超过25可能造成访问报错
     * 可选
     * 缺省值：20
     */
    private Integer offset;

    /**
     * 当前页数
     * 最大翻页数100
     * 可选
     * 缺省值：1
     */
    private Integer page;

    /**
     * 返回结果控制
     * 此项默认返回基本地址信息；取值为all返回地址信息、附近POI、道路以及道路交叉口信息。
     * 可选
     * 缺省值：base
     */
    private String extensions;

    /**
     * 数字签名
     * 可选
     */
    @JsonIgnore
    private String sig;

    /**
     * 返回数据格式类型
     * 可选值：JSON，XML
     * 可选
     * 缺省值：JSON
     */
    private String output;

    /**
     * 回调函数
     * callback值是用户定义的函数名称，此参数只在output=JSON时有效
     */
    private String callback;

}
