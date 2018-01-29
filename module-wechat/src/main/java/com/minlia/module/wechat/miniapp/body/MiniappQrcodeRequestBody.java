package com.minlia.module.wechat.miniapp.body;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class MiniappQrcodeRequestBody {

    @NotNull
    private String scene;

    @NotNull
    private String page;

    private Integer width;

    private Boolean autoColor;

    private Object lineColor;

}
