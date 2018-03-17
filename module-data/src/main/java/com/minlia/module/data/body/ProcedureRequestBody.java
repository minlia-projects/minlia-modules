package com.minlia.module.data.body;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * 存储过程请求body
 * Created by garen on 2018/3/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcedureRequestBody<T> {

    private static final String SUCCESS_CODE = "100000";

    private T payload;

    private String r_code;

    private String r_meg;

    public boolean isSuccess(){
        return StringUtils.isNotBlank(r_code) && r_code.equals(SUCCESS_CODE);
    }

}


