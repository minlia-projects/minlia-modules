package com.minlia.module.pooul.body.common;

import lombok.Data;

@Data
public class PooulResponseBody implements PooulApiHttpResponseBody {

    private String code;

    private String msg;

    @Override
    public Boolean isSuccess() {
        return false;
    }

}
