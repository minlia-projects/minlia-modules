package com.minlia.module.pooul.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/9/6.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PooulMerchantDO extends AbstractEntity{

    /**
     * 平台商户ID
     */
    private String platformMerchantId;

    /**
     * 父级商户ID
     */
    private String parentId;

    /**
     * 商户ID
     */
    private String merchantId;

    /**
     * 用户GUI，属于那个用户的
     */
    private String guid;

    /**
     * 商户名称
     */
    private String name;

}
