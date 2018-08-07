//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.minlia.module.wechat.material.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WxMpMaterialBatchGetResult implements Serializable {

    @SerializedName("item_count")
    @JsonProperty("item_count")
    private int itemCount;

    @SerializedName("total_count")
    @JsonProperty("total_count")
    private int totalCount;

    private List<MaterialItem> item;


}
