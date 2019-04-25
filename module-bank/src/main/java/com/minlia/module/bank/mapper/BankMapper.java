package com.minlia.module.bank.mapper;

import com.minlia.module.bank.entity.Bank;

import java.util.List;

public interface BankMapper {

    void create(Bank bank);

    void update(Bank bank);

    void delete(String number);

    Bank one(Bank bank);

    List<Bank> list(Bank bank);

}