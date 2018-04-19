package com.minlia.modules.tencent.cloud.auth.body;

import lombok.Data;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class TcFaceIdResponseBody{

    private String code;

    private String msg;

    private TcFaceIdResult result;

    public boolean isSuccess(){
        return code.equals("0");
    }

}