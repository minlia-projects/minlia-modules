package com.minlia.module.realname.bean;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.http.HttpStatus;

@NoArgsConstructor
@Data
@Builder
@AllArgsConstructor
public class SysRealName3Dto {

    //{
    //    "code": "200",
    //        "message": "调用成功",
    //        "requestId": "E54F5B39-6726-4174-8C84-FDBA2E40FEFC",
    //        "data": {
    //    "bizCode": "2",
    //            "ispName": "CUCC"
    //}
    //}
    //
    //bizCode取值范围:
    //1: 校验一致
    //2: 校验不一致
    //3: 查无记录

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("message")
    private String message;
    @JsonProperty("requestId")
    private String requestId;
    @JsonProperty("data")
    private DataDTO data;

    @NoArgsConstructor
    @Data
    public static class DataDTO {
        @JsonProperty("bizCode")
        private Integer bizCode;
        @JsonProperty("ispName")
        private String ispName;

        public boolean isSuccess() {
            return bizCode.equals(NumberUtils.INTEGER_ONE);
        }
    }

    public boolean isSuccess() {
        return code == HttpStatus.OK.value();
    }

    public boolean isFailure() {
        return code != HttpStatus.OK.value();
    }

}
