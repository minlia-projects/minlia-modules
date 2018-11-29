package com.minlia.module.richtext.bean;

import com.minlia.module.data.bean.QueryRequest;
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
public class RichtextQO extends QueryRequest {

    private String type;

    private String code;

    private String title;

}
