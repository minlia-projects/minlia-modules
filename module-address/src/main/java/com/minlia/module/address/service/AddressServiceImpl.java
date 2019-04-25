package com.minlia.module.address.service;

import com.minlia.cloud.code.SystemCode;
import com.minlia.cloud.utils.ApiAssert;
import com.minlia.module.address.entity.Address;
import com.minlia.module.address.ro.AddressQRO;
import com.minlia.module.address.ro.AddressCRO;
import com.minlia.module.address.ro.AddressURO;
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
    public Address create(AddressCRO cro) {
        String guid = SecurityContextHolder.getCurrentGuid();
        long count = this.count(AddressQRO.builder().guid(guid).enabled(true).build());
        ApiAssert.state(count <= 5, AddressCode.Message.ADD_UP_TO_5_ADDRESSES);
        Address address = mapper.map(cro, Address.class);
        address.setGuid(guid);
        address.setDef(count > 0 ? false : true);
        addressMapper.create(address);
        return address;
    }

    @Override
    public Address update(AddressURO uto) {
        Address address = this.queryOne(AddressQRO.builder().id(uto.getId()).guid(SecurityContextHolder.getCurrentGuid()).enabled(true).build());
        ApiAssert.notNull(address, SystemCode.Message.DATA_NOT_EXISTS);
        mapper.map(uto, address);
        addressMapper.update(address);
        return address;
    }

    @Override
    public int updateDefault(Long id) {
        String guid = SecurityContextHolder.getCurrentGuid();
        Address address = this.queryOne(AddressQRO.builder().id(id).guid(guid).enabled(true).build());
        ApiAssert.notNull(address, SystemCode.Message.DATA_NOT_EXISTS);
        return addressMapper.updateDefault(guid, id);
    }

    @Override
    public int delete(Long id) {
        Address address = this.queryOne(AddressQRO.builder().id(id).guid(SecurityContextHolder.getCurrentGuid()).enabled(true).build());
        ApiAssert.notNull(address, SystemCode.Message.DATA_NOT_EXISTS);
        address.setEnabled(false);
        return addressMapper.update(address);
    }

    @Override
    public long count(AddressQRO qro) {
        return addressMapper.count(qro);
    }

    @Override
    public Address queryOne(AddressQRO qro) {
        return addressMapper.queryOne(qro);
    }

    @Override
    public List<Address> queryList(AddressQRO qro) {
        return addressMapper.queryList(qro);
    }

}
