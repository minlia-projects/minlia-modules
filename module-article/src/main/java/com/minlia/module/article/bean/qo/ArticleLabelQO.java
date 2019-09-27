package com.minlia.module.article.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleLabelQO extends QueryRequest {

    @Override
    public String getSortsStr() {
        return StringUtils.isBlank(super.getSortsStr()) ? "lastModifiedDate.DESC" : super.getSortsStr();
    }

    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 是否启用
     */
    private Boolean enabled;

}