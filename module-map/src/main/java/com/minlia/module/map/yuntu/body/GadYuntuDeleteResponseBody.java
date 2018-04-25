package com.minlia.module.map.yuntu.body;

import lombok.Data;

/**
 * Created by garen on 2017/12/27.
 */
@Data
public class GadYuntuDeleteResponseBody {

    /**
     * 返回的状态信息
     * status = 1，info返回“ok”
     */
    private String info;

    /**
     * 1：成功；0：失败
     */
    private Integer status;

    /**
     * 删除成功数目
     */
    private Integer success;

    /**
     * 删除失败数目
     */
    private Integer fail;

    public boolean isSuccess(){
        return this.status.equals(1);
    }

}
