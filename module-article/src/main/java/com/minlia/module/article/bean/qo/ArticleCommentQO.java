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
public class ArticleCommentQO extends QueryRequest {

    @Override
    public String getSortsStr() {
        return StringUtils.isBlank(super.getSortsStr()) ? "lastModifiedDate.DESC" : super.getSortsStr();
    }
    /**
     * ID
     */
    private Long id;

    /**
     * 文章ID
     */
    private Long articleId;

    /**
     * 创建人
     */
    private String createBy;

}