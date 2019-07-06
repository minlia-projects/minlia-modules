package com.minlia.module.article.ro;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCollectionQRO extends QueryRequest {

    private Long articleId;

    private String collector;

    private LocalDateTime collectionDate;

}