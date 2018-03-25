package com.minlia.module.data.batis.query;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.toolkit.SqlUtils;
import com.baomidou.mybatisplus.toolkit.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义查询构造器
 */
public class AbstractQuery<T> extends EntityWrapper<T> {
    /**
     * 外键表别名对象
     */
    protected Map<String, String> aliasMap = new HashMap<>();

    @Override
    public String getSqlSelect() {
        return StringUtils.isEmpty(this.sqlSelect) ? "*" : SqlUtils.stripSqlInjection(this.sqlSelect);
    }

    public Map<String, String> getAliasMap() {
        return aliasMap;
    }

    /**
     * 创建外键表关联对象,需要在mapper(xml)中编写join
     */
    public void createAlias(String entiry, String alias) {
        this.aliasMap.put(entiry, alias);
    }
}
