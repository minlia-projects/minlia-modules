package com.minlia.module.pooul.bean.qo;

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
public class PooulMerchatInternalQO {

    private String platformMerchantId;

    private String parentId;

    private String guid;

    private String number;

}
