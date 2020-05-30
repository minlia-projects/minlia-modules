package com.minlia.module.data.datascopee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据范围
 *
 * @author garen
 * @date 2018/4/17 下午2:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataScopeParam {

    private DataScopeDimensionEnum dimension = DataScopeDimensionEnum.ORGINATION_ID;

    /**
     * 限制范围的字段名称
     */
    private String name = "org_id";

    /**
     * 具体的数据范围
     */
    private List<Long> values = new ArrayList<>();

    /**
     * 是否只查询本部门
     */
    private Boolean isOnly = false;

}