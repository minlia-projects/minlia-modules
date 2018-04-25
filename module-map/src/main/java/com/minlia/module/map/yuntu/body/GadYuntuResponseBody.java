package com.minlia.module.map.yuntu.body;

import lombok.Data;

/**
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuResponseBody {

    private String info;

    private String infocode;

    private Integer status;

    private Long _id;

    private String message;

    public boolean isSuccess(){
        return this.status.equals(1);
    }

}
