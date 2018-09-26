package com.minlia.module.bank.mapper;

import com.minlia.module.bank.bean.domain.BankDO;

import java.util.List;

public interface BankMapper {

    void create(BankDO bankDo);

    void update(BankDO bankDo);

    void delete(String number);

    BankDO queryByNumber(String number);

    List<BankDO> queryList();

}