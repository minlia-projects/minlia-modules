package com.minlia.module.bank.mapper;

import com.minlia.module.bank.domain.BankDo;
import com.minlia.module.bank.domain.BankcodeDo;

import java.util.List;

public interface BankMapper {

    void create(BankDo bankDo);

    void update(BankDo bankDo);

    void delete(String number);

    BankcodeDo queryByNumber(String number);

    List<BankDo> queryList();

}