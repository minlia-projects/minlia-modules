package com.minlia.module.wechat.material.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class NewsItem {

    private String title;
    private String author;
    private String digest;
    private String content;
    @SerializedName("content_source_url")
    @JsonProperty("content_source_url")
    private String contentSourceUrl;
    @SerializedName("thumb_media_id")
    @JsonProperty("thumb_media_id")
    private String thumbMediaId;
    @SerializedName("show_cover_pic")
    @JsonProperty("show_cover_pic")
    private Integer showCoverPic;
    private String url;
    @SerializedName("thumb_url")
    @JsonProperty("thumb_url")
    private String thumbUrl;
    @SerializedName("need_open_comment")
    @JsonProperty("need_open_comment")
    private Integer needOpenComment;
    @SerializedName("only_fans_can_comment")
    @JsonProperty("only_fans_can_comment")
    private Integer onlyFansCanComment;

}