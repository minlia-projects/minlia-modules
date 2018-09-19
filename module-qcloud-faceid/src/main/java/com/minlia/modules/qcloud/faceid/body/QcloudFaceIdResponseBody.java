package com.minlia.modules.qcloud.faceid.body;

import lombok.Data;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class QcloudFaceIdResponseBody {

    private String code;

    private String msg;

    private QcloudFaceIdResult result;

    public boolean isSuccess(){
        return code.equals("0");
    }

}