package com.minlia.module.address.service;

import com.minlia.module.address.bean.domain.AddressDO;
import com.minlia.module.address.bean.qo.AddressQO;
import com.minlia.module.address.bean.to.AddressCTO;
import com.minlia.module.address.bean.to.AddressUTO;

import java.util.List;

/**
 * 用户地址接口
 * Created by garen on 2018/8/17.
 */
public interface AddressService {

    AddressDO create(AddressCTO cto);

    AddressDO update(AddressUTO uto);

    int updateDefault(Long id);

    int delete(Long id);

    long count(AddressQO qo);

    AddressDO queryOne(AddressQO qo);

    List<AddressDO> queryList(AddressQO qo);

}
