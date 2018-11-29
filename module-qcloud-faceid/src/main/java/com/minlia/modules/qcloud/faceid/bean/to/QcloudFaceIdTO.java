package com.minlia.modules.qcloud.faceid.bean.to;

import com.minlia.cloud.body.ApiRequestBody;
import lombok.Data;

/**
 * Created by garen on 2018/4/19.
 */
@Data
public class QcloudFaceIdTO implements ApiRequestBody {

    private String name;

    private String idNo;

//    private String orderNo;
//
//    private String userId;

}
