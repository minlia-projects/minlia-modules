package com.minlia.module.bankbranch.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bankbranch.entity.BankBranch;
import com.minlia.module.bankbranch.ro.BankBranchQRO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankBranchService {

//    void init();

    /**
     * 创建
     * @param BankcodeDo
     * @return
     */
    BankBranch create(BankBranch BankcodeDo);

    /**
     * 修改
     * @param BankcodeDo
     * @return
     */
    BankBranch update(BankBranch BankcodeDo);

    /**
     * 删除
     * @param number
     */
    void delete(String number);

    boolean exists(String number);

    BankBranch queryByNumber(String number);

    List<BankBranch> queryList(BankBranchQRO qo);

    PageInfo<BankBranch> queryPage(BankBranchQRO qo, Pageable pageable);

}
