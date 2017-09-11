package com.kdniao.endpoint.requestBody;

import javax.validation.constraints.NotNull;

/**
 * Created by qianyi on 2017/9/6.
 * 快递公司单号实时查询请求体
 */

public class TrackQueryRequestBody {

    //String expCode, String expNo
    /**
     * 快递公司编码
     */
    @NotNull
    private  String expCode;

    /**
     * 快递公司单号
     */
    @NotNull
    private  String expNo;

    public String getExpCode() {
        return expCode;
    }

    public void setExpCode(String expCode) {
        this.expCode = expCode;
    }

    public String getExpNo() {
        return expNo;
    }

    public void setExpNo(String expNo) {
        this.expNo = expNo;
    }
}
