package com.minlia.module.data.bean;


import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.Body;
import com.minlia.cloud.constant.SymbolConstants;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * Created by garen on 2017/7/15
 * TODO  要不要把分页放这里
 *
 * @author garen
 */
@Data
public class QueryRequest implements Body {

    private Integer page;

    private Integer size;

    public QueryRequest() {
        page = 0;
        size = 15;
    }

    //排序参数，前端传过来的 TODO
    @JsonProperty("sorts")
    private String sortsStr;

    public int getPageSize() {
        return this.size;
    }

    public int getPageNumber() {
        return this.page;
    }

    private static final String DOT_ASC = ".ASC";
    private static final String DOT_DESC = ".DESC";

    private static final String SPACE_ASC = " asc";
    private static final String SPACE_DESC = " desc";

    private static final String ORDER_BY = " order by ";

    public Page getPage() {
        Page page = new Page(this.getPageNumber(), this.getPageSize());
        if (StringUtils.isNotBlank(sortsStr)) {
            page.addOrder(getOrderItems());
        }
        return page;
    }

    public List<OrderItem> getOrderItems() {
        List<OrderItem> orderItems = Lists.newArrayList();
        for (String s : sortsStr.split(SymbolConstants.ZHX)) {
            String[] ss = s.split(SymbolConstants.DOT_ZY);
            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ss[0]);
            orderItems.add(ss[1].equals("ASC") ? OrderItem.asc(column) : OrderItem.desc(column));
        }
        return orderItems;
    }

    public boolean hasOrder() {
        return Objects.nonNull(sortsStr);
    }

//    public String getOrderBy() {
//        //id.DESC-name.ASC to id DESC,name ASC
//        return StringUtils.isNotBlank(sortsStr) ?
//                ORDER_BY + CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortsStr.replace(DOT_ASC, SPACE_ASC).replace(DOT_DESC, SPACE_DESC).replace(SymbolConstants.ZHX, SymbolConstants.COMMA)) :
//                SymbolConstants.EMPTY;
//    }

    public static void main(String[] args) {
        QueryRequest request = new QueryRequest();
        request.setSortsStr("createDate.DESC");
//        System.out.println(request.getOrderBy());

        String sortsStr = "idCd.DESC-name.ASC";
        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortsStr.replace(DOT_ASC, SPACE_ASC).replace(DOT_DESC, SPACE_DESC).replace(SymbolConstants.ZHX, SymbolConstants.COMMA)));

        List<OrderItem> orderItems = Lists.newArrayList();
        for (String s : sortsStr.split(SymbolConstants.ZHX)) {
            String[] ss = s.split(SymbolConstants.DOT_ZY);
            String column = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, ss[0]);
            orderItems.add(ss[1].equals("ASC") ? OrderItem.asc(column) : OrderItem.desc(column));
        }
        System.out.println(orderItems);
    }
}
