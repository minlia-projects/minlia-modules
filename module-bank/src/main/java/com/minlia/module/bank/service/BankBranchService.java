package com.minlia.module.bank.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.bean.domain.BankBranchDO;
import com.minlia.module.bank.bean.qo.BankBranchQO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankBranchService {

    void init();

    /**
     * 创建
     * @param BankcodeDo
     * @return
     */
    BankBranchDO create(BankBranchDO BankcodeDo);

    /**
     * 修改
     * @param BankcodeDo
     * @return
     */
    BankBranchDO update(BankBranchDO BankcodeDo);

    /**
     * 删除
     * @param number
     */
    void delete(String number);

    boolean exists(String number);

    BankBranchDO queryByNumber(String number);

    List<BankBranchDO> queryList(BankBranchQO qo);

    PageInfo<BankBranchDO> queryPage(BankBranchQO qo, Pageable pageable);

}
