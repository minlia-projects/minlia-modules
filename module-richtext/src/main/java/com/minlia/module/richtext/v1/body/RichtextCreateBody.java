package com.minlia.module.richtext.v1.body;

import com.minlia.boot.v1.body.ApiRequestBody;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/6/30.
 */
@Data
public class RichtextCreateBody implements ApiRequestBody {

    @NotNull
    private String type;

    @NotNull
    private String title;

    @NotNull
    private String code;

    @NotNull
    private String content;

    private String note;

}
