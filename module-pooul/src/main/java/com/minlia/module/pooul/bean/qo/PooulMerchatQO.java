package com.minlia.module.pooul.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
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
public class PooulMerchatQO {

    /**
     * 用户GUID
     */
    private String guid;

    /**
     * 入驻商户编号（merchant_id）
     */
    private Long merchantId;

    /**
     * 父级商户编号，输入此参数查询该父级商户所有下一级入驻商户
     */
    private Long parent_id;

    /**
     * 平台商户编号
     */
    private Long platformMerchantId;

}
