package com.minlia.module.address.service;

import com.minlia.module.address.entity.Address;
import com.minlia.module.address.ro.AddressQRO;
import com.minlia.module.address.ro.AddressCRO;
import com.minlia.module.address.ro.AddressURO;

import java.util.List;

/**
 * 用户地址接口
 * Created by garen on 2018/8/17.
 */
public interface AddressService {

    Address create(AddressCRO cro);

    Address update(AddressURO uto);

    int updateDefault(Long id);

    int delete(Long id);

    long count(AddressQRO qro);

    Address queryOne(AddressQRO qro);

    List<Address> queryList(AddressQRO qro);

}
