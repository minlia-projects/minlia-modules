//package com.minlia.module.realname.bean;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor
//@Data
//public class SysRealNameDto<T> {
//
//    @JsonProperty("code")
//    private Integer code;
//
//    @JsonProperty("message")
//    private String message;
//
//    public boolean isSuccess() {
//        return code == 0;
//    }
//
//    public boolean isFailure() {
//        return code != 0;
//    }
//
//}