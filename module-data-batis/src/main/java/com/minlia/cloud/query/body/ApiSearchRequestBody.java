package com.minlia.cloud.query.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.minlia.cloud.query.condition.QueryCondition;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

//@JSONType(ignores = {"data"})
public class ApiSearchRequestBody<PAYLOAD extends SearchRequestBody> implements Serializable {
    @JsonIgnore
    private List<QueryCondition> conditions = Lists.newArrayList();

    @ApiModelProperty(value = "搜索具体请求体(包括各搜索字段的实际请求体)")
    private PAYLOAD payload;

    public PAYLOAD getPayload() {
        return payload;
    }

    public void setPayload(PAYLOAD payload) {
        this.payload = payload;
    }

    public ApiSearchRequestBody() {
    }


    public List<QueryCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<QueryCondition> conditions) {
        this.conditions = conditions;
    }
}
