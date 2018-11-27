package com.minlia.module.data.body;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import com.minlia.module.common.constant.SymbolConstants;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.StringJoiner;

/**
 * Created by garem on 2017/7/15
 * TODO  要不要把分页放这里
 */
public class QueryRequest implements Body {

    @JsonIgnore
    private String query;

    @JsonIgnore
    private Integer page;

    @JsonIgnore
    private Integer size;

    //排序
    private List<SortBody> sorts;

    @Data
    public static class SortBody {

        @ApiModelProperty(example = "id")
        private String filed;

        @ApiModelProperty(example = "DESC")
        private Sort.Direction direction;

    }

    @JsonProperty("sort")
    public String getSort() {
        StringJoiner sj = new StringJoiner(SymbolConstants.COMMA);
        for (SortBody sort : sorts) {
            StringBuilder sb = new StringBuilder();
            sb.append(sort.getFiled()).append(SymbolConstants.SPACE).append(sort.getDirection().name());
            sj.add(sb.toString());
        }
        return sj.toString();
    }

}
