package com.minlia.module.bank.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.bean.domain.BankDo;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/8/10.
 */
public interface BankService {

    BankDo create(BankDo bankDo);

    BankDo update(BankDo bankDo);

    void delete(String number);

    BankDo queryByNumber(String number);

    List<BankDo> queryList();

    PageInfo<BankDo> queryPage(Pageable pageable);
}
