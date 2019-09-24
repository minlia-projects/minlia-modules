package com.minlia.module.data.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;
import com.minlia.cloud.body.Body;
import com.minlia.module.common.constant.SymbolConstants;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

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
    @JsonProperty("sorts")
    private String sortStrs;

    @JsonIgnore
    private List<Sort> sorts;

    public List<Sort> getSorts() {
        if (StringUtils.isNotBlank(sortStrs)) {
            List<Sort> list = Lists.newArrayList();
            String[] sorts = sortStrs.split(SymbolConstants.ZHX);
            for (String sortStr : sorts) {
                String[] sortStr1 = sortStr.split(SymbolConstants.DOT_);
                Sort sort = new Sort(sortStr1[0], Sort.Direction.valueOf(sortStr1[1]));
                list.add(sort);
            }
            return list;
        }
        return null;
    }

//    public static List<Sort> getSorts(String sortStrs) {
//        List<Sort> list = Lists.newArrayList();
//        if (StringUtils.isNotBlank(sortStrs)) {
//            String[] sorts = sortStrs.split(SymbolConstants.COMMA);
//            for (String sortStr : sorts) {
//                String[] sortStr1 = sortStr.split(SymbolConstants.DOT_);
//                Sort sort = new Sort(sortStr1[0], Sort.Direction.valueOf(sortStr1[1]));
//                list.add(sort);
//            }
//        }
//        return list;
//    }
//
    public static void main(String[] args) {

        QueryRequest request = new QueryRequest();
        request.setSortStrs("createDate.ASC-asdfds.DESC");
        request.getSorts();
    }

//    @JsonProperty("sort")
//    public String getSort() {
//        StringJoiner sj = new StringJoiner(SymbolConstants.COMMA);
//        for (Sort sort : sorts) {
//            StringBuilder sb = new StringBuilder();
//            sb.append(sort.getFiled()).append(SymbolConstants.SPACE).append(sort.getDirection().name());
//            sj.add(sb.toString());
//        }
//        return sj.toString();
//
//    }

}
