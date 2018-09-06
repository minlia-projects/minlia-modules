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

    private String platformMerchantId;

    private String parentId;

    private String guid;

    private String name;

    private String number;

}
