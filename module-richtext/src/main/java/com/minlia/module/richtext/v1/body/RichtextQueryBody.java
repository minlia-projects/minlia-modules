package com.minlia.module.richtext.v1.body;

import com.minlia.module.data.query.v2.body.SearchRequestBody;
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
public class RichtextQueryBody implements SearchRequestBody {

    private Long id;

    private String type;

    private String title;

    private String code;

}
