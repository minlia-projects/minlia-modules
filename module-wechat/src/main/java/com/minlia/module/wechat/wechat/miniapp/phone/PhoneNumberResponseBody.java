package com.minlia.module.wechat.wechat.miniapp.phone;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.minlia.cloud.body.Body;
import lombok.Data;

/**
 * Created by will on 9/12/17.
 */
@Data
public class PhoneNumberResponseBody implements Body {

//  {
//    "phoneNumber": "13580006666",
//      "purePhoneNumber": "13580006666",
//      "countryCode": "86",
//      "watermark":
//    {
//      "appid":"APPID",
//        "timestamp":TIMESTAMP
//    }
//  }

  @JsonProperty
  private String phoneNumber;

  @JsonProperty
  private String purePhoneNumber;

  @JsonProperty
  private String countryCode;

  @JsonProperty
  private Watermark watermark;

}
