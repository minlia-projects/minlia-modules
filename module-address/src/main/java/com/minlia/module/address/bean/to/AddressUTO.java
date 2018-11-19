package com.minlia.module.address.bean.to;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * Created by garen on 2017/6/30.
 */
@Data
@ApiModel("用户地址-修改")
public class AddressUTO extends AddressCTO {

    private Long id;

}
