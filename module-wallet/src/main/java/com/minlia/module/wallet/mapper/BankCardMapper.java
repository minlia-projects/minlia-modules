package com.minlia.module.wallet.mapper;

import com.minlia.module.wallet.domain.BankCardDo;
import com.minlia.module.wallet.dto.BankCardQueryDto;
import com.minlia.module.wallet.vo.BankCardVo;

import java.util.List;

public interface BankCardMapper {

    void create(BankCardDo bankCard);

    void update(BankCardDo bankCard);

    void setWithdraw(String guid,Long id);

    void delete(Long id);

    BankCardVo queryById(Long id);

    long count(BankCardQueryDto dto);

    BankCardVo queryOne(BankCardQueryDto dto);

    List<BankCardVo> queryList(BankCardQueryDto dto);
}