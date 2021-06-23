package com.minlia.aliyun.green.bean;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AliyunGreenContentCro {

    @NotBlank
    private String content;

}