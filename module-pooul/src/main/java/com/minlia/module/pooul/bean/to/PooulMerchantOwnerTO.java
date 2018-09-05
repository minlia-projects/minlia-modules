package com.minlia.module.pooul.bean.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/9/5.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulMerchantOwnerTO {

    /**
     * 身份证件类型，目前只支持身份证，固定值为1
     * 必填
     */
    private String idcard_type;

    /**
     * 真实姓名
     * 必填
     */
    private String name;

    /**
     * 身份证号码
     * 可选
     */
    private String idcard_num;

    /**
     * 手机号码
     * 可选
     */
    private String mobile;

}
