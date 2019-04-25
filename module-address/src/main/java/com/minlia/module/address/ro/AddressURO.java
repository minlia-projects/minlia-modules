package com.minlia.module.address.ro;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by garen on 2017/6/30.
 */
@Data
@ApiModel("用户地址-修改")
public class AddressURO extends AddressCRO {

    private Long id;

}
