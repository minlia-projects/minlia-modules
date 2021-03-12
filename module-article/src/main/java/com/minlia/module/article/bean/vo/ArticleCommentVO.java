package com.minlia.module.article.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 文章评论
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCommentVO {

    @ApiModelProperty(value = "文章ID")
    private Long articleId;

    @ApiModelProperty(value = "操作人")
    private Long operator;

    @ApiModelProperty(value = "时间")
    private LocalDateTime time;

    @ApiModelProperty(value = " 内容")
    private String content;

    @ApiModelProperty(value = " 创建人")
    private Creator creator;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Creator {
        /**
         * 昵称
         */
        private String nickname;

        /**
         * 头像
         */
        private String avatar;

        /**
         * 评论人
         */
        private Long createBy;
    }

}