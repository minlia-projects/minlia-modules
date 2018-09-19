package com.minlia.module.gad.yuntu.body;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuSearchResponseBody {

    private String info;

    private String infocode;

    private Integer status;

    private Integer count;

    private List<Map<String,Object>> datas;

    public boolean isSuccess(){
        return this.status.equals(1);
    }

}
