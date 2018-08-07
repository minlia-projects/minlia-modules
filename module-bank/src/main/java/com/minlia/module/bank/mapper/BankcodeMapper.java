package com.minlia.module.bank.mapper;

import com.minlia.module.bank.domain.BankcodeDo;
import com.minlia.module.bank.dto.BankcodeQueryDto;

import java.util.List;

public interface BankcodeMapper {

    void create(BankcodeDo bankCard);

    void update(BankcodeDo bankCard);

    void delete(String number);

    BankcodeDo queryByNumber(String number);

    BankcodeDo queryOne(BankcodeQueryDto dto);

    List<BankcodeDo> queryList(BankcodeQueryDto dto);

}