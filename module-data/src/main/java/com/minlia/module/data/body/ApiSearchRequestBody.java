package com.minlia.module.data.body;//package com.minlia.hummerpay.userinfo.bean.qo;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.google.common.collect.Lists;
//import com.minlia.module.data.query.v2.QueryCondition;
//import io.swagger.annotations.ApiModelProperty;
//
//import java.io.Serializable;
//import java.util.List;
//
////@JSONType(ignores = {"data"})
//public class ApiSearchRequestBody<PAYLOAD extends SearchRequestBody> implements Serializable {
//
//    @JsonIgnore
//    private List<QueryCondition> conditions = Lists.newArrayList();
//
//    @ApiModelProperty(value = "sort")
//    private List<SortBody> sorts;
//
//    @ApiModelProperty(value = "搜索具体请求体(包括各搜索字段的实际请求体)")
//    private PAYLOAD payload;
//
//    public ApiSearchRequestBody() {
//    }
//
//    public ApiSearchRequestBody(PAYLOAD payload) {
//        this.payload = payload;
//    }
//
//    public ApiSearchRequestBody(PAYLOAD payload, List<SortBody> sorts) {
//        this.payload = payload;
//        this.sorts = sorts;
//    }
//
//    public List<QueryCondition> getConditions() {
//        return conditions;
//    }
//
//    public void setConditions(List<QueryCondition> conditions) {
//        this.conditions = conditions;
//    }
//
//    public List<SortBody> getSorts() {
//        return sorts;
//    }
//
//    public void setSorts(List<SortBody> sorts) {
//        this.sorts = sorts;
//    }
//
//    public PAYLOAD getPayload() {
//        return payload;
//    }
//
//    public void setPayload(PAYLOAD payload) {
//        this.payload = payload;
//    }
//
//}
