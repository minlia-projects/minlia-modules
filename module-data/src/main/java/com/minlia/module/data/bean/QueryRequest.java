package com.minlia.module.data.bean;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;
import com.minlia.cloud.body.Body;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.common.constant.SymbolConstants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Created by garem on 2017/7/15
 * TODO  要不要把分页放这里
 */
public class QueryRequest implements Body {

    //    @JsonIgnore
    @Min(0)
    private Integer page;

    //    @JsonIgnore
    @Max(3000)
    private Integer size;

    public QueryRequest() {
        page = 0;
        size = 15;
    }

    @JsonProperty("sorts")
    private String sortsStr;

    public int getPageSize() {
        return this.size;
    }

    public int getPageNumber() {
        return this.page;
    }

    public String getSortsStr() {
        return sortsStr;
    }

    @Value("${system.pagination.minimum-page-number:0}")
    public Integer minimumPageSize;

    @Value("${system.pagination.maximumPageSize:2000}")
    public Integer maximumPageSize;

    public void setPage(Integer page) {
        ApiAssert.state(size < minimumPageSize, "MINIMUM_PAGINATION_NUMBER", "Minimum value cannot be less than" + minimumPageSize);
        this.page = page;
    }

    public void setSize(Integer size) {
        ApiAssert.state(size > maximumPageSize, "MAXIMUM_PAGINATION_SIZE", "Maximum value cannot be greater than" + maximumPageSize);
        this.size = size;
    }

    private static final String DOT_ASC = ".ASC";
    private static final String DOT_DESC = ".DESC";

    private static final String SPACE_ASC = " asc";
    private static final String SPACE_DESC = " desc";

    public String getOrderBy() {
        return StringUtils.isNotBlank(sortsStr) ? CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortsStr.replace(DOT_ASC, SPACE_ASC).replace(DOT_DESC, SPACE_DESC).replace(SymbolConstants.ZHX, SymbolConstants.COMMA)) : null;
    }

    public static void main(String[] args) {
        String sortsStr = "id.DESC-name.ASC";
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortsStr.replace(DOT_ASC, SPACE_ASC).replace(DOT_DESC, SPACE_DESC).replace(SymbolConstants.ZHX, SymbolConstants.COMMA)));
    }

}