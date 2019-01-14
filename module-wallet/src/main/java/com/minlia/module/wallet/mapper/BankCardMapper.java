package com.minlia.module.wallet.mapper;

import com.minlia.module.wallet.bean.domain.BankCardDO;
import com.minlia.module.wallet.bean.to.BankCardQO;
import com.minlia.module.wallet.bean.vo.BankCardVo;

import java.util.List;

public interface BankCardMapper {

    void create(BankCardDO bankCard);

    void update(BankCardDO bankCard);

    void setWithdraw(String guid,Long id);

    void delete(Long id);

    BankCardVo queryById(Long id);

    long count(BankCardQO qo);

    BankCardVo queryOne(BankCardQO qo);

    List<BankCardVo> queryList(BankCardQO qo);
}