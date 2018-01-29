package com.minlia.module.wechat.wechat.miniapp.phone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import lombok.Data;

/**
 * Created by will on 9/12/17.
 */
@Data
public class Watermark implements Body {

  @JsonProperty
  private String appid;

  @JsonProperty
  private String timestamp;

}
