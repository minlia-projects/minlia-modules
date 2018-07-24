package com.minlia.module.pooul.body.pay;

import com.minlia.module.pooul.body.common.PooulApiHttpResponseBody;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Created by garen on 2018/07/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulPayNotifyResponseBody implements PooulApiHttpResponseBody {

//    eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJjb2RlIjowLCJkYXRhIjp7ImJhbmtfdHlwZSI6IkNGVCIsImNhc2hfZmVlIjoxLCJmZWVfdHlwZSI6IkNOWSIsIm91dF90cmFkZV9pZCI6IjQyMDAwMDAxMDkyMDE4MDcyNDMwNDgyNzk2ODgiLCJzdWJfaXNfc3Vic2NyaWJlIjoiTiIsInN1Yl9vcGVuaWQiOiJvZXJRQTVRNWNsVEFLOGVBM3RHTk9BaXo3czRvIiwidG90YWxfZmVlIjoxLCJ0cmFkZV9pZCI6IjViNTZjZDliMDFjOTExMzA1ZDFmZjFjYSIsIm1jaF90cmFkZV9pZCI6Im40R0J4UmJ6WE0iLCJtZXJjaGFudF9pZCI6IjIxNjIyODg4MDc0NDM0MzciLCJwYXlfdHlwZSI6IndlY2hhdC5qc21pbmlwZyIsInRyYWRlX3N0YXRlIjowLCJ0cmFkZV9pbmZvIjoi5Lqk5piT5oiQ5YqfLCAifSwibm9uY2Vfc3RyIjoiNWI1NmNkZmIwMWM5MTEzMDVkMWZmMWNlIn0.qYLaY6Gcc9-yEFmCMBCJhcuplpJe-HF2pq1Yz2WRrXQj2UjedBiJxAiiA_8Aag1JqkR-NNw-1v_QCTmOo4qiYl-OXwZ2d-AdiDjqNSFCHYzUoBelZBCdBdiJWOShFAz2aWik83XaMgnE7KRPNW0v6JIf_ir9XfRYMCOkX7w1vdg

    private Integer code;

    private String nonceStr;

    private PooulPayNotifyData data;

    @Override
    public Boolean isSuccess() {
        return null != code && code.equals(NumberUtils.INTEGER_ZERO);
    }

}
