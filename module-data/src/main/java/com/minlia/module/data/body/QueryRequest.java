package com.minlia.module.data.body;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.minlia.cloud.body.Body;
import com.minlia.module.common.constant.SymbolConstants;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by garem on 2017/7/15
 * 标识为搜索请求体
 */
public class QueryRequest implements Body {

    @JsonIgnore
    private String query;

    private Integer page;

    private Integer size;

    //排序
    private List<SortBody> sorts;

    public String getSorts() {
        StringJoiner sj = new StringJoiner(SymbolConstants.COMMA);
        for (SortBody sort : sorts) {
            StringBuilder sb = new StringBuilder();
            sb.append(sort.getFiled()).append(SymbolConstants.SPACE).append(sort.getDirection().name());
            sj.add(sb.toString());
        }
        return sj.toString();
    }

}
