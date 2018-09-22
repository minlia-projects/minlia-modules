package com.minlia.module.pooul.bean.dto;

import lombok.Data;

import java.util.Map;

/**
 * Created by garen on 2018/9/21.
 */
@Data
public class PooulLoginDTO extends PooulDTO {

    private Map data;

    private Map debug;

    private Float time_elapsed;

}
