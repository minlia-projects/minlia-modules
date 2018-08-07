package com.minlia.module.wechat.material.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class MaterialItem implements Serializable {

    @JsonIgnore
    private Long id;

    @SerializedName("media_id")
    @JsonProperty("media_id")
    private String mediaId;

    @SerializedName("update_time")
    @JsonProperty("update_time")
    private Long updateTime;

    private Content content;

    @Data
    public static class Content {

        @SerializedName("create_time")
        @JsonProperty("create_time")
        private Long createTime;

        @SerializedName("update_time")
        @JsonProperty("update_time")
        private Long updateTime;

        @SerializedName("news_item")
        @JsonProperty("news_item")
        List<NewsItem> newsItem;

    }

}