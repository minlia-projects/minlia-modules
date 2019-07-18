package com.minlia.module.bank.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.entity.Bank;
import com.minlia.module.bank.mapper.BankMapper;
import com.minlia.module.bank.service.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by garen on 2018/8/10.
 */
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankMapper bankMapper;

    @Override
    public Bank create(Bank bank) {
        return null;
    }

    @Override
    public Bank update(Bank bank) {
        return null;
    }

    @Override
    public void delete(String number) {
        bankMapper.delete(number);
    }

    @Override
    public Bank one(Bank bank) {
        return bankMapper.one(bank);
    }

    @Override
    public List<Bank> list(Bank bank) {
        return bankMapper.list(bank);
    }

    @Override
    public PageInfo<Bank> page(Bank bank, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize(), qro.getOrderBy()).doSelectPageInfo(()-> bankMapper.list(bank));
    }

}
