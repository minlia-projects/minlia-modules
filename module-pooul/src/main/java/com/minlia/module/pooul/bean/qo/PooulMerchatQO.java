package com.minlia.module.pooul.bean.qo;

import lombok.Data;

/**
 * Created by garen on 2018/9/5.
 */
@Data
public class PooulMerchatQO {

    /**
     * 入驻商户编号（merchant_id）
     */
    private Long _id;

    /**
     * 父级商户编号，输入此参数查询该父级商户所有下一级入驻商户
     */
    private Long parent_id;

}
