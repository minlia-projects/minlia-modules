package com.minlia.module.address.bean.qo;

import com.minlia.module.data.bean.QueryRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户
 * Created by will on 6/21/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressQO extends QueryRequest {

    /**
     * 地址ID
     */
    private Long id;

    /**
     * 用户GUID
     */
    private String guid;

    /**
     * 默认地址
     */
    private Boolean def;

    /**
     * 是否启用
     */
    private Boolean enabled;

}
