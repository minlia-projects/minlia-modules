package com.minlia.module.address.mapper;

import com.minlia.module.address.bean.domain.AddressDO;
import com.minlia.module.address.bean.qo.AddressQO;

import java.util.List;

/**
 * Created by garen on 2018/1/21.
 */
public interface AddressMapper {

    int create(AddressDO addressDO);

    int update(AddressDO addressDO);

    int updateDefault(String guid, Long id);

    int delete(Long id);

    long count(AddressQO qo);

    AddressDO queryOne(AddressQO qo);

    List<AddressDO> queryList(AddressQO qo);

}
