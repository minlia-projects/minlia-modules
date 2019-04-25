package com.minlia.module.bank.service;

import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.entity.Bank;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by garen on 2018/8/10.
 */
public interface BankService {

    Bank create(Bank bank);

    Bank update(Bank bank);

    void delete(String number);

    Bank one(Bank bank);

    List<Bank> list(Bank bank);

    PageInfo<Bank> page(Bank bank, Pageable pageable);

}
