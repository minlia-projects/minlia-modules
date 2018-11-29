package com.minlia.module.data.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import com.minlia.module.common.constant.SymbolConstants;
import lombok.Data;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by garem on 2017/7/15
 * TODO  要不要把分页放这里
 */
@Data
public class QueryRequest implements Body {

    @JsonIgnore
    private String query;

    @JsonIgnore
    private Integer page;

    @JsonIgnore
    private Integer size;

    //排序
    private List<Sort> sorts;

    @JsonProperty("sort")
    public String getSort() {
        StringJoiner sj = new StringJoiner(SymbolConstants.COMMA);
        for (Sort sort : sorts) {
            StringBuilder sb = new StringBuilder();
            sb.append(sort.getFiled()).append(SymbolConstants.SPACE).append(sort.getDirection().name());
            sj.add(sb.toString());
        }
        return sj.toString();
    }

}
