package com.minlia.module.map.geocode.body.response;

import lombok.Data;

/**
 * Created by garen on 2018/4/25.
 */
@Data
public class GadRegeoResponseBody {

    private Integer status;

    private String info;

    private Integer infocode;

    private RegeocodeBody regeocode;

    public Boolean isSuccess(){
        return this.status.equals(1);
    }
}
