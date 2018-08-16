package com.minlia.module.wallet.mapper;

import com.minlia.module.wallet.domain.BankCardDo;
import com.minlia.module.wallet.bean.to.BankCardQueryTo;
import com.minlia.module.wallet.bean.vo.BankCardVo;

import java.util.List;

public interface BankCardMapper {

    void create(BankCardDo bankCard);

    void update(BankCardDo bankCard);

    void setWithdraw(String guid,Long id);

    void delete(Long id);

    BankCardVo queryById(Long id);

    long count(BankCardQueryTo dto);

    BankCardVo queryOne(BankCardQueryTo dto);

    List<BankCardVo> queryList(BankCardQueryTo dto);
}