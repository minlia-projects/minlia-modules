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
public class PooulMerchatInternalQO extends QueryRequest {

    private String platformMerchantId;

    private String parentId;

    private String merchantId;

    private String guid;

    private String number;

}
