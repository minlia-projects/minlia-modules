package com.minlia.module.bankbranch.mapper;

import com.minlia.module.bankbranch.bean.domain.BankBranchDO;
import com.minlia.module.bankbranch.bean.qo.BankBranchQO;

import java.util.List;

public interface BankBranchMapper {

    void create(BankBranchDO bankCard);

    void update(BankBranchDO bankCard);

    void delete(String number);

    BankBranchDO queryByNumber(String number);

    long count(BankBranchQO qo);

    BankBranchDO queryOne(BankBranchQO qo);

    List<BankBranchDO> queryList(BankBranchQO qo);

}