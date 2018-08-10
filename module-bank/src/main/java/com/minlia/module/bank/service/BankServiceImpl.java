package com.minlia.module.bank.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.minlia.module.bank.domain.BankDo;
import com.minlia.module.bank.mapper.BankMapper;
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
    public BankDo create(BankDo bankDo) {
        return null;
    }

    @Override
    public BankDo update(BankDo bankDo) {
        return null;
    }

    @Override
    public void delete(String number) {
        bankMapper.delete(number);
    }

    @Override
    public BankDo queryByNumber(String number) {
        return bankMapper.queryByNumber(number);
    }

    @Override
    public List<BankDo> queryList() {
        return bankMapper.queryList();
    }

    @Override
    public PageInfo<BankDo> queryPage(Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()-> bankMapper.queryList());
    }

}
