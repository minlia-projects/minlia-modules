package com.minlia.module.richtext.body;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2017/7/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichtextQueryRequestBody implements ApiRequestBody {

    private String type;

    private String code;

    private String title;

}
