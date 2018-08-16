package com.minlia.module.bank.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.bean.domain.BankBranchDo;
import com.minlia.module.bank.bean.qo.BankBranchQo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BankBranchService {

    void init();

    /**
     * 创建
     * @param BankcodeDo
     * @return
     */
    BankBranchDo create(BankBranchDo BankcodeDo);

    /**
     * 修改
     * @param BankcodeDo
     * @return
     */
    BankBranchDo update(BankBranchDo BankcodeDo);

    /**
     * 删除
     * @param number
     */
    void delete(String number);

    boolean exists(String number);

    BankBranchDo queryByNumber(String number);

    List<BankBranchDo> queryList(BankBranchQo qo);

    PageInfo<BankBranchDo> queryPage(BankBranchQo qo, Pageable pageable);

}
