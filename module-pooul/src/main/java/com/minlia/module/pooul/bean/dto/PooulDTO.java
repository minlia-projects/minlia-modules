package com.minlia.module.pooul.bean.dto;

import lombok.Data;

@Data
public class PooulDTO implements PooulApiHttpDTO {

    private Integer code;

    private String msg;

    private String debug;

    @Override
    public Boolean isSuccess() {
        return code == 0;
    }

}
