//package com.minlia.cloud.query.specification.jpa.body;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.google.common.collect.Lists;
//import com.minlia.cloud.body.Body;
//import com.minlia.cloud.query.body.QueryRequestBody;
//import com.minlia.cloud.query.specification.jpa.QueryCondition;
//import io.swagger.annotations.ApiModelProperty;
//
//import java.util.List;
//
////@JSONType(ignores = {"data"})
//public class JpaApiSearchRequestBody<PAYLOAD extends QueryRequestBody> implements Body {
//    @JsonIgnore
//    private List<QueryCondition> conditions = Lists.newArrayList();
//
//    @ApiModelProperty(value = "搜索具体请求体(包括各搜索字段的实际请求体)")
//    private PAYLOAD payload;
//
//    public PAYLOAD getPayload() {
//        return payload;
//    }
//
//    public void setPayload(PAYLOAD payload) {
//        this.payload = payload;
//    }
//
//    public JpaApiSearchRequestBody() {
//    }
//
//
//    public List<QueryCondition> getConditions() {
//        return conditions;
//    }
//
//    public void setConditions(List<QueryCondition> conditions) {
//        this.conditions = conditions;
//    }
//}
