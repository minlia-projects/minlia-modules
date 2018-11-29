package com.minlia.modules.qcloud.faceid.bean.domain;

import com.minlia.module.data.entity.AbstractEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by garen on 2018/4/19.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QcloudFaceIdRecord extends AbstractEntity {

    /**
     * 订单号，由合作方上送，每次唯一，不能超过 32 位
     */
    private String orderNo;

    /**
     * 姓名
     */
    private String name;

    /**
     * idNo
     */
    private String idNo;

    /**
     * 用户 ID ，用户的唯一标识（不能带有特殊字符）
     */
    private String userId;

    /**
     * 是否通过
     */
    private Boolean isAuth;

}
