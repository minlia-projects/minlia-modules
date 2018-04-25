package com.minlia.module.map.yuntu.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * 本地检索请求体
 * Created by garen on 2017/12/27.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GadYuntuSearchLocalRequestBody extends GadYuntuAbstractRequestBody {

    /**
     * 搜索关键词,支持0-9数字，大小写字母（a-z,A-Z）以及所有中文字符
     */
    @NotNull
    private String keywords;

    /**
     * 设定检索的城市名（中文名称）
     * 1. 支持全国/省/市/区县行政区划范围的检索；
     * 2. city=全国，即对用户全表搜索；
     * 3. 当city值设置非法或不正确时，按照city = 全国返回。
     */
    @NotNull
    private String city;

    /**
     * 过滤条件
     * 1.对建立【排序筛选索引字段】进行检索；
     * 2. 请在数据管理台添加或删除筛选排序索引字段，系统默认为_id，_name，_address，_updatetime，_createtime（其中_updatetime,_createtime暂只能用于排序，不能作为过滤字段）
     * 3. 支持多个筛选条件并行使用：多个过滤条件之间使用“+”（代表与关系）；
     * 4. 支持对文本类型的字段进行精确匹配；
     * 5. 支持对整数和小数字段的连续区间筛选。
     * 规则：
     * filter=key1:value1+key2:[value2,value3]
     * 示例：
     * filter=type:酒店+star:[3,5]
     * （等同于SQL语句的：
     * WHERE type = "酒店"
     * AND star BETWEEN 3 AND 5）
     */
    private String filter;

    /**
     * 排序规则
     * 两种方式排序：
     * 1.云图系统预设排序法：_weight：相关度权重值排序，返回由高到低的排序检索结果；
     * 规则:sortrule = _weight:0 //降序
     * 2. 支持对在云图数据管理台建立【排序筛选索引字段】的整数或小数字段排序；
     * 规则：
     * sortrule=字段名:1 （升序）；
     * sortrule=字段名:0 （降序）；
     * 示例：
     * 按年龄age字段升序排序，sortrule=age:1
     */
    private String sortrule;
//
//    /**
//     * 分页数据条目数（每页数据展现的条数）
//     * 最大每页记录数为100
//     * 默认 20
//     */
//    private Integer limit;
//
//    /**
//     * 分页索引，当前页数
//     * 默认 1
//     */
//    private Integer page;

}
