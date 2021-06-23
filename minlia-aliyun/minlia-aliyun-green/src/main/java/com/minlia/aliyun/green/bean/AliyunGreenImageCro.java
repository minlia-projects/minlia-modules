package com.minlia.aliyun.green.bean;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;

@Data
public class AliyunGreenImageCro {

    @NotBlank
    @URL
    private String url;

}