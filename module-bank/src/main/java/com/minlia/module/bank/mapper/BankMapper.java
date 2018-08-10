package com.minlia.module.bank.mapper;

import com.minlia.module.bank.domain.BankDo;

import java.util.List;

public interface BankMapper {

    void create(BankDo bankDo);

    void update(BankDo bankDo);

    void delete(String number);

    BankDo queryByNumber(String number);

    List<BankDo> queryList();

}