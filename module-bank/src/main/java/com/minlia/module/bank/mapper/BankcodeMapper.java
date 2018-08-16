package com.minlia.module.bank.mapper;

import com.minlia.module.bank.bean.domain.BankBranchDo;
import com.minlia.module.bank.bean.qo.BankBranchQo;

import java.util.List;

public interface BankcodeMapper {

    void create(BankBranchDo bankCard);

    void update(BankBranchDo bankCard);

    void delete(String number);

    BankBranchDo queryByNumber(String number);

    long count(BankBranchQo qo);

    BankBranchDo queryOne(BankBranchQo qo);

    List<BankBranchDo> queryList(BankBranchQo qo);

}