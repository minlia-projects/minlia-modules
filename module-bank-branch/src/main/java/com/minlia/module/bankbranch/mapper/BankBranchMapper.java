package com.minlia.module.bankbranch.mapper;

import com.minlia.module.bankbranch.entity.BankBranch;
import com.minlia.module.bankbranch.ro.BankBranchQRO;

import java.util.List;

public interface BankBranchMapper {

    void create(BankBranch bankCard);

    void update(BankBranch bankCard);

    void delete(String number);

    BankBranch queryByNumber(String number);

    long count(BankBranchQRO qo);

    BankBranch queryOne(BankBranchQRO qo);

    List<BankBranch> queryList(BankBranchQRO qo);

}