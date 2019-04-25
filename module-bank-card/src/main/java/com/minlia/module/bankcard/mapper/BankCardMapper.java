package com.minlia.module.bankcard.mapper;

import com.minlia.module.bankcard.entity.BankCard;
import com.minlia.module.bankcard.ro.BankCardQRO;
import com.minlia.module.bankcard.vo.BankCardVO;

import java.util.List;

public interface BankCardMapper {

    void create(BankCard bankCard);

    void update(BankCard bankCard);

    void setWithdraw(String guid,Long id);

    void delete(Long id);

    BankCardVO queryById(Long id);

    long count(BankCardQRO qo);

    BankCardVO queryOne(BankCardQRO qo);

    List<BankCardVO> queryList(BankCardQRO qo);
}