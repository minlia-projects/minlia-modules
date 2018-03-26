package com.minlia.module.data.scope;

import java.util.List;
import lombok.Data;

/**
 * @author will
 */
@Data
public class DataScope {

    /**
     * 限制范围的字段名称
     */
    private String scopeName = "guid";

    /**
     * 具体的数据范围
     */
    private List<Integer> scopeValue;

    public DataScope() {
    }
}
