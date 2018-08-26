package com.minlia.module.pooul.bean.dto;

import lombok.Data;

@Data
public class PooulDTO implements PooulApiHttpDTO {

    private String code;

    private String msg;

    @Override
    public Boolean isSuccess() {
        return false;
    }

}
