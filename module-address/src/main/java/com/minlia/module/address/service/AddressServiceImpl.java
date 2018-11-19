package com.minlia.module.address.service;

import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.address.bean.domain.AddressDO;
import com.minlia.module.address.bean.qo.AddressQO;
import com.minlia.module.address.bean.to.AddressCTO;
import com.minlia.module.address.bean.to.AddressUTO;
import com.minlia.module.address.constant.AddressCode;
import com.minlia.module.address.mapper.AddressMapper;
import com.minlia.modules.rbac.context.SecurityContextHolder;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by garen on 2018/9/11.
 */
@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private Mapper mapper;

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public AddressDO create(AddressCTO cto) {
        String guid = SecurityContextHolder.getCurrentGuid();
        long count = this.count(AddressQO.builder().guid(guid).enabled(true).build());
        ApiAssert.state(count <= 5, AddressCode.Message.ADD_UP_TO_5_ADDRESSES);
        AddressDO addressDO = mapper.map(cto, AddressDO.class);
        addressDO.setGuid(guid);
        addressDO.setDef(count > 0 ? false : true);
        addressMapper.create(addressDO);
        return addressDO;
    }

    @Override
    public AddressDO update(AddressUTO uto) {
        AddressDO addressDO = this.queryOne(AddressQO.builder().id(uto.getId()).guid(SecurityContextHolder.getCurrentGuid()).enabled(true).build());
        ApiAssert.notNull(addressDO, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, addressDO);
        addressMapper.update(addressDO);
        return addressDO;
    }

    @Override
    public int updateDefault(Long id) {
        String guid = SecurityContextHolder.getCurrentGuid();
        AddressDO addressDO = this.queryOne(AddressQO.builder().id(id).guid(guid).enabled(true).build());
        ApiAssert.notNull(addressDO, SystemCode.Message.DATA_NOT_EXISTS);
        return addressMapper.updateDefault(guid, id);
    }

    @Override
    public int delete(Long id) {
        AddressDO addressDO = this.queryOne(AddressQO.builder().id(id).guid(SecurityContextHolder.getCurrentGuid()).enabled(true).build());
        ApiAssert.notNull(addressDO, SystemCode.Message.DATA_NOT_EXISTS);
        addressDO.setEnabled(false);
        return addressMapper.update(addressDO);
    }

    @Override
    public long count(AddressQO qo) {
        return addressMapper.count(qo);
    }

    @Override
    public AddressDO queryOne(AddressQO qo) {
        return addressMapper.queryOne(qo);
    }

    @Override
    public List<AddressDO> queryList(AddressQO qo) {
        return addressMapper.queryList(qo);
    }

}
