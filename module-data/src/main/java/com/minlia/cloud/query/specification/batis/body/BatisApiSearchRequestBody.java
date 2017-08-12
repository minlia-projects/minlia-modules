package com.minlia.cloud.query.specification.batis.body;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.Body;
import com.minlia.cloud.query.body.SearchRequestBody;
import com.minlia.cloud.query.specification.batis.QueryCondition;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

//@JSONType(ignores = {"payload"})
public class BatisApiSearchRequestBody<PAYLOAD extends SearchRequestBody> implements Body {
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

    public BatisApiSearchRequestBody() {
    }


    public List<QueryCondition> getConditions() {
        return conditions;
    }

    public void setConditions(List<QueryCondition> conditions) {
        this.conditions = conditions;
    }
}
