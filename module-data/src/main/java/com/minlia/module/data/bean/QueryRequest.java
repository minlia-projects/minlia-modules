package com.minlia.module.data.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Sets;
import com.minlia.cloud.body.Body;
import com.minlia.module.common.constant.SymbolConstants;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Set;
import java.util.StringJoiner;

/**
 * Created by garem on 2017/7/15
 * TODO  要不要把分页放这里
 */
@Data
public class QueryRequest implements Body {

    @JsonIgnore
    private String query;

    //    @JsonIgnore
    private Integer page;

    //    @JsonIgnore
    private Integer size;

    public QueryRequest() {
        page = 0;
        size = 15;
    }

    //排序参数，前端传过来的 TODO
    @JsonProperty("sorts")
    private String sortsStr;

//    @JsonIgnore
//    private Set<Sort> sorts;
//
//    public Set<Sort> getSorts() {
//        if (null == sorts) {
//            sorts = splitSortsStr(sortsStr);
//        }
//        return sorts;
//    }
//
//    public void addSort(String filed, Sort.Direction direction) {
//        if (null == sorts) {
//            sorts = Sets.newHashSet();
//        }
//        sorts.add(new Sort(filed, direction));
//    }
//
//    private static Set<Sort> splitSortsStr(String sortsStr) {
//        if (StringUtils.isNotBlank(sortsStr)) {
//            Set<Sort> set = Sets.newHashSet();
//            String[] sortsArray = sortsStr.split(SymbolConstants.ZHX);
//            for (int i = 0; i < sortsArray.length; i++) {
//                String[] sortArray = sortsArray[i].split(SymbolConstants.DOT_ZY);
//                Sort sort = new Sort(sortArray[0], Sort.Direction.valueOf(sortArray[1]));
//                set.add(sort);
//            }
//            return set;
//        } else {
//            return null;
//        }
//    }

    public int getPageSize() {
        return this.size;
    }

    public int getPageNumber() {
        return this.page;
    }


//    @JsonProperty("sort")
//    public String getSort() {
//        if (CollectionUtils.isNotEmpty(sorts)) {
//            StringJoiner sj = new StringJoiner(SymbolConstants.COMMA);
//            for (Sort sort : sorts) {
//                StringBuilder sb = new StringBuilder();
//                sb.append(sort.getFiled()).append(SymbolConstants.SPACE).append(sort.getDirection().name());
//                sj.add(sb.toString());
//            }
//            return sj.toString();
//        } else {
//            return null;
//        }
//    }


    private static final String DOT_ASC = ".ASC";
    private static final String DOT_DESC = ".DESC";

    private static final String SPACE_ASC = " asc";
    private static final String SPACE_DESC = " desc";

    public String getOrderBy() {
        //id.DESC-name.ASC to id DESC,name ASC
        return StringUtils.isNotBlank(sortsStr) ?
                CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortsStr.replace(DOT_ASC, SPACE_ASC).replace(DOT_DESC, SPACE_DESC).replace(SymbolConstants.ZHX, SymbolConstants.COMMA)) :
                null;
    }

//    public static void main(String[] args) {
//        QueryRequest request = new QueryRequest();
//        request.setSortsStr("createDate.DESC");
//        System.out.println(request.getOrderBy());

//        String sortsStr = "id.DESC-name.ASC";
//        System.out.println(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, sortsStr.replace(DOT_ASC, SPACE_ASC).replace(DOT_DESC, SPACE_DESC).replace(SymbolConstants.ZHX, SymbolConstants.COMMA)));
//    }
}
