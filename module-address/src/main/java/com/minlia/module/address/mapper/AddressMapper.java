package com.minlia.module.address.mapper;

import com.minlia.module.address.entity.Address;
import com.minlia.module.address.ro.AddressQRO;

import java.util.List;

/**
 * Created by garen on 2018/1/21.
 */
public interface AddressMapper {

    int create(Address address);

    int update(Address address);

    int updateDefault(String guid, Long id);

    int delete(Long id);

    long count(AddressQRO qro);

    Address queryOne(AddressQRO qro);

    List<Address> queryList(AddressQRO qro);

}
