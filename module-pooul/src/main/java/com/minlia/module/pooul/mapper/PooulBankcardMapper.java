package com.minlia.module.pooul.mapper;

import com.minlia.module.pooul.bean.domain.PooulBankCardDO;

/**
 * Created by garen on 2018/9/6.
 */
public interface PooulBankcardMapper {

    int create(PooulBankCardDO pooulBankCardDO);

    int delete(Long recordId);

}
