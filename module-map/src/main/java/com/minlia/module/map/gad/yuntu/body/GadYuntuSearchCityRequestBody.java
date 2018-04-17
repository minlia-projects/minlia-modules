package com.minlia.module.map.gad.yuntu.body;

import lombok.Data;

/**
 * 本地检索请求体
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuSearchCityRequestBody extends GadYuntuAbstractRequestBody {

    /**
     * 搜索关键词,支持0-9数字，大小写字母（a-z,A-Z）以及所有中文字符
     */
    private String keywords;

    /**
     * 规则： 省中文名称；
     * 说明：
     * 1. province = 全国，返回含有数据的所有市名称以及对应数据量，并根据数据量从高到低排行；
     * 2. province = 山东，返回沈阳含有数据的所有市名称以及对应数据量，并根据数据量从高到低排行；
     */
    private String province;

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

}
