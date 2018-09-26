package com.minlia.module.bank.mapper;

import com.minlia.module.bank.bean.domain.BankBranchDO;
import com.minlia.module.bank.bean.qo.BankBranchQO;

import java.util.List;

public interface BankcodeMapper {

    void create(BankBranchDO bankCard);

    void update(BankBranchDO bankCard);

    void delete(String number);

    BankBranchDO queryByNumber(String number);

    long count(BankBranchQO qo);

    BankBranchDO queryOne(BankBranchQO qo);

    List<BankBranchDO> queryList(BankBranchQO qo);

}