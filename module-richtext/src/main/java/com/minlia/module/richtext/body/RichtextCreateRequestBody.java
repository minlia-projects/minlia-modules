package com.minlia.module.richtext.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by garen on 2017/6/30.
 */
@Data
public class RichtextCreateRequestBody implements ApiRequestBody {

    @NotNull
    private String type;

    @NotNull
    private String code;

    @NotNull
    private String title;

    @NotNull
    private String content;

    private String note;

}
