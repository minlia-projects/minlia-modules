package com.minlia.module.pooul.body.jsminipg;

import com.minlia.module.pooul.body.common.PooulApiHttpResponseBody;
import lombok.Data;

/**
 * Created by garen on 2018/07/17.
 */
@Data
public class PooulWechatJsminipgResponseBody extends PooulApiHttpResponseBody {

//  {"appId":"wx26a0ffd6bfa99df7","timeStamp":"1526429363","nonce_str":"hXJ2e3dQZm8qwZlc","package":"prepay_id=wx1608092325202618ae9afa6d0781719733","signType":"MD5","paySign":"94095737486E1394D9534599B7A978D5"}

    private Integer code;

    private String msg;

    private String nonceStr;

    private String time_elapsed;

    private String data;

}
