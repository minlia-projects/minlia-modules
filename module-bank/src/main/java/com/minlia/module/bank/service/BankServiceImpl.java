package com.minlia.module.bank.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.minlia.module.bank.bean.domain.BankDO;
import com.minlia.module.bank.mapper.BankMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by garen on 2018/8/10.
 */
@Service
public class BankServiceImpl implements BankService {

    @Autowired
    private BankMapper bankMapper;

    @Override
    public BankDO create(BankDO bankDo) {
        return null;
    }

    @Override
    public BankDO update(BankDO bankDo) {
        return null;
    }

    @Override
    public void delete(String number) {
        bankMapper.delete(number);
    }

    @Override
    public BankDO one(BankDO bankDO) {
        return bankMapper.one(bankDO);
    }

    @Override
    public List<BankDO> list(BankDO bankDO) {
        return bankMapper.list(bankDO);
    }

    @Override
    public PageInfo<BankDO> page(BankDO bankDO, Pageable pageable) {
        return PageHelper.startPage(pageable.getPageNumber(),pageable.getPageSize()).doSelectPageInfo(()-> bankMapper.list(bankDO));
    }

}
