package com.minlia.module.wallet.mapper;

import com.minlia.module.wallet.bean.domain.BankCardDo;
import com.minlia.module.wallet.bean.to.BankCardQO;
import com.minlia.module.wallet.bean.vo.BankCardVo;

import java.util.List;

public interface BankCardMapper {

    void create(BankCardDo bankCard);

    void update(BankCardDo bankCard);

    void setWithdraw(String guid,Long id);

    void delete(Long id);

    BankCardVo queryById(Long id);

    long count(BankCardQO qo);

    BankCardVo queryOne(BankCardQO qo);

    List<BankCardVo> queryList(BankCardQO qo);
}