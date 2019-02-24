package com.minlia.module.data.bean;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    private Set<Sort> sorts;

    private Set<Sort> getSorts(){
        if (null == sorts) {
            sorts = splitSortsStr(sortsStr);
        }
        return sorts;
    }

    private static Set<Sort> splitSortsStr(String sortsStr) {
        if (StringUtils.isNotBlank(sortsStr)) {
            Set<Sort> set = Sets.newHashSet();
            String[] sortsArray = sortsStr.split(SymbolConstants.ZHX);
            for (int i = 0; i < sortsArray.length; i++) {
                String[] sortArray = sortsArray[i].split(SymbolConstants.DOT_ZY);
                Sort sort = new Sort(sortArray[0], Sort.Direction.valueOf(sortArray[1]));
                set.add(sort);
            }
            return set;
        } else {
            return null;
        }
    }

    public int getPageSize() {
        return this.size;
    }

    public int getPageNumber() {
        return this.page;
    }




    @JsonProperty("sort")
    public String getSort() {
        if (CollectionUtils.isNotEmpty(sorts)) {
            StringJoiner sj = new StringJoiner(SymbolConstants.COMMA);
            for (Sort sort : sorts) {
                StringBuilder sb = new StringBuilder();
                sb.append(sort.getFiled()).append(SymbolConstants.SPACE).append(sort.getDirection().name());
                sj.add(sb.toString());
            }
            return sj.toString();
        } else {
            return null;
        }
    }

}
